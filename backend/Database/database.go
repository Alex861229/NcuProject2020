package database

import(
    "database/sql"
    _ "github.com/go-sql-driver/mysql"
)
const(
    users string = "ncu_project_14"
    password string = "ncupropject14"
    host string = "140.115.87.221:3306"
    databaseName string = "mydb"
)

type MySQLCli struct {
  db *sql.DB
}

var instanceMySQLCli *MySQLCli = nil

func Connect() (db *sql.DB, err error) {
  if instanceMySQLCli == nil {
      instanceMySQLCli = new(MySQLCli)
      var err error
      instanceMySQLCli.db, err = sql.Open("mysql", "ncu_project_14:ncuproject14@tcp(140.115.87.221:3306)/mydb")
      if err != nil {
          return nil, err
      }
  }

  return instanceMySQLCli.db, nil
}

func Close() {
  if instanceMySQLCli != nil {
      instanceMySQLCli.db.Close()

  }
}