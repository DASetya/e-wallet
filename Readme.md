# E-wallet Backend

Final Assignment Backend Technology MT sg-edts.

### Step to set up project

1. Pastikan sudah terinstall [Java 17](https://jdk.java.net/17/ "Java 17") dan [PostgreSQL](https://www.postgresql.org/download/ "PostgreSQL") pada device yang digunakan
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

### Contoh Request dan Response

Contoh request dan response dapat dilihat pada file BE Task