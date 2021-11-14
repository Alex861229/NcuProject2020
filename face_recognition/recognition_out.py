# -*- coding: UTF-8 -*-
import sys,os,dlib,glob,numpy
from skimage import io
import cv2
import imutils
import numpy as np
import time
import datetime
import threading
import itertools
from queue import Queue

# 資料庫Start
import mysql.connector
maxdb = mysql.connector.connect(
  host = "140.115.87.221",
  user = "ncu_project_14",
  password = "ncuproject14",
  database = "mydb",
  )
cursor=maxdb.cursor()
CourseID = str(sys.argv[1])
student_sql = "SELECT user.Image FROM user, studentcourse WHERE UserID = studentcourse.StudentID and studentcourse.CourseID = '%s'" % CourseID
cursor.execute(student_sql)
student = cursor.fetchall()
student = np.array(student)

teacher_sql = "SELECT user.Image FROM user, teachercourse WHERE UserID = teachercourse.TeacherID and teachercourse.CourseID = '%s'" % CourseID
cursor.execute(teacher_sql)
teacher = cursor.fetchall()
teacher = np.array(teacher)

member = np.append(student,teacher) 
# 資料庫End

# Thread start
def Record():
  # 每10秒寫入資料庫】
  time.sleep(10)
  UserID = facerecord.get()

  #計算 period
  time_sql = "SELECT facerecord.created_at FROM facerecord WHERE (CourseID = '%s' and UserID = '%s' and status = 'in') ORDER BY id DESC LIMIT 1" % (CourseID, UserID)
  cursor.execute(time_sql)
  last_time = cursor.fetchone()
  nowTime = datetime.datetime.now()
  t = (nowTime - last_time[0]).seconds
  print(t)

  Insert_sql = "INSERT INTO facerecord (UserID, CourseID, status, period) VALUES (%s,%s,%s,%s)"
  records = [(UserID, CourseID, 'out', t)]
  cursor.executemany(Insert_sql, records)
  maxdb.commit()
  print('%s 已出教室！' % UserID)
# Thread end

tStart = time.time()#計時開始

# 人臉68特徵點模型路徑
predictor_path = "shape_predictor_68_face_landmarks.dat"

# 人臉辨識模型路徑
face_rec_model_path = "dlib_face_recognition_resnet_model_v1.dat"

# 比對人臉圖片資料夾名稱
faces_folder_path = "./rec"


# 載入人臉檢測器
detector = dlib.get_frontal_face_detector()

# 載入人臉特徵點檢測器
sp = dlib.shape_predictor(predictor_path)

# 比對人臉描述子列表
descriptors = []

# 比對人臉名稱列表
candidate = []

# 記錄到的臉部列表
global facerecord
facerecord = Queue()

# 紀錄時間商數
last = [0]

# 載入人臉辨識檢測器
facerec = dlib.face_recognition_model_v1(face_rec_model_path)

# 針對比對資料夾裡每張圖片做比對:
# 1.人臉偵測
# 2.特徵點偵測
# 3.取得描述子
for f in glob.glob(os.path.join(faces_folder_path, "*.jpg")):
    base = os.path.basename(f)
    # 依序取得圖片檔案人名
    if base in member :
      candidate.append(os.path.splitext(base)[0])
      img = io.imread(f)

      # 1.人臉偵測
      dets = detector(img, 1)

      for k, d in enumerate(dets):
        # 2.特徵點偵測
        shape = sp(img, d)
 
        # 3.取得描述子，128維特徵向量
        face_descriptor = facerec.compute_face_descriptor(img, shape)

        # 轉換numpy array格式
        v = numpy.array(face_descriptor)
        descriptors.append(v)
        # 針對需要辨識的人臉同樣進行處理

#計時結束
tEnd = time.time()

#列印結果
print ("It cost %f sec" % (tEnd - tStart))#會自動做近位

# 需要辨識的人臉圖片名稱
cap=cv2.VideoCapture(0, cv2.CAP_DSHOW)

while True:

  ret,img=cap.read()
  if not ret :
    continue
  cv2.imwrite("face.jpg",img)

  img = io.imread('./face.jpg')
  dets = detector(img, 1)
  
  dist = []
  for k, d in enumerate(dets):
    shape = sp(img, d)
    face_descriptor = facerec.compute_face_descriptor(img, shape)
    d_test = numpy.array(face_descriptor)

    x1 = d.left()
    y1 = d.top()
    x2 = d.right()
    y2 = d.bottom()
    # 以方框標示偵測的人臉
    cv2.rectangle(img, (x1, y1), (x2, y2), ( 0, 255, 0), 4, cv2. LINE_AA)
   
    # 計算歐式距離
    for i in descriptors:
      dist_ = numpy.linalg.norm(i -d_test)
      dist.append(dist_)

  if(dist != []):
    # 將比對人名和比對出來的歐式距離組成一個dict
    c_d = dict( zip(candidate,dist))

    # 根據歐式距離由小到大排序
    cd_sorted = sorted(c_d.items(), key = lambda d:d[ 1])
    if(cd_sorted[0][1] < 0.5):
      # 取得最短距離就為辨識出的人名
      rec_name = cd_sorted[0][0]

      # 判斷人物是否在queue內，避免重複紀錄
      if rec_name not in list(facerecord.queue) :
        facerecord.put(rec_name)
        # 建立一個子執行緒
        t = threading.Thread(target = Record)
        # 執行該子執行緒
        t.start()

    # 無法比對
    else:
      print('unknown')

  # 若按下 q 鍵則離開迴圈
  if cv2.waitKey(1) & 0xFF == ord('q'):
    break

# 釋放攝影機
cap.release()