# E-wallet Backend

Final Assignment Backend Technology MT sg-edts.

### Step to set up project

1. Pastikan sudah terinstall [Java 17][Java 17] dan [PostgreSQL][PostgreSQL] pada device yang digunakan
2. Buat database dengan nama wallet atau sesuaikan dengan file pada application.properties
3. Jalankan project

### Endpoints

- POST /user/registration
- GET /user/{username}/getbalance
- GET /user/{username}/getinfo
- PUT /user/{username}/unban
- PUT /user/{username}/addktp
- POST /user/changepassword
- POST /transaction/create
- POST /transaction/topup
- GET /report/getreport/{date}

### Contoh Request Body

> POST /user/registration
Url : localhost:8080/user/registration
Request body

```json
{
	"username":"aku",
	"password" : "Inip@ssw0rd"
}
```
> GET /user/{username}/getinfo
Url : localhost:8080/user/aku/getbalance

> GET /user/{username}/getbalance
Url : localhost:8080/user/dimas/getinfo

> PUT /user/{username}/unban
Url : localhost:8080/user/aku/unban

> PUT /user/{username}/addktp
Url : localhost:8080/user/dimas/addktp

```json
{
    "ktp": "1023487126481200"
}
```

> POST /user/changepassword
Url : localhost:8080/user/changepassword

```json
{
    "username" : "aku",
    "oldPassword" : "Inip@ssw0rd",
    "newPassword" : "Inip@ssw0rdstrong"
}
```

> POST /transaction/create
Url : localhost:8080/transaction/create

```json
{
	"username": "aku",
    "password" : "Inip@ssw0rdstrong",
	"destinationUsername": "kamu",
	"amount": 10000,
	"status": "SETTLED"
}
```
> POST /transaction/topup
Url : localhost:8080/transaction/topup

```json
{
    "username" : "aku",
    "password" : "Inip@ssw0rdstrong",
    "amount" : 100000
}
```
> GET /report/getreport/{date}
Url : localhost:8080/report/getreport/2023-02-02

### Requirements
Requirements project dapat dilihat pada file BE Task