# Supplier Hub

**(EN)** Supplier Hub is an application used for sellers to be able to record their suppliers and their order to restock the commodity.

**(IN)** Supplier Hub adalah aplikasi yang digunakan bagi para pedagang untuk dapat mencatat suppliernya dan melakukan pesanannya untuk mengisi kembali barang. 

## Feature
- Login
- Item Category
- Commodity
- Supplier
- Transaction with Supplier

## Installation
### Git Clone

**(EN)** Clone the code from github using git clone by typing 

**(IN)** Melakukan kloning kode dari github dengan menggunakan git clone dengan mengetik 

```bash
git clone < repository that will be used >
```
**(EN)** In this case, git clone syntax it will be like this

**(IN)** Dalam hal ini, git clone akan seperti berikut
```bash
$ git clone https://github.com/wirahmat/supplierhub.git
```
### Import As Maven Project
**(EN)** After download or clone the repository, you can import the project as a maven project in your desire IDE

**(IN)** Setelah mengunduh atau melakukan kloning repository, kamu bisa melakukan impor projek sebagai maven project ke dalam IDE yang diinginkan

### Create Initial Database

**(EN)** Before you can run the application, you need to create your initial database using postgres with database name "supplier-hub"

**(IN)** Sebelum kamu dapat menjalankan aplikasi, kamu perlu membuat database awal menggunakan postgres dengan nama database "supplier-hub"

### Run The Application

**(EN)** Then, you can run the application after importing the project and building the application is finished

**(IN)** Lalu, kamu bisa menjalankan aplikasi setelah melakukan import project dan building aplikasi telah selesai

## Usage
**(EN)** For the API documentation you can open the swagger after running the application by accesing:

**(IN)** Untuk dokumentasi API kamu bisa membuka swagger setelah menjalankan aplikasi dengan mengakses:

**[Swagger Link](http://localhost:8080/swagger-ui/index.html)**.

Default User:
```json
username : user1@live.com
password : Password123*
```

## Contributing

Pull requests are welcome. For major changes, please open an issue first
to discuss what you would like to change.

Please make sure to update tests as appropriate.

## Technology Used
- Spring Boot 3.3.1
- PostgresSql 14.9
- Flyway 10.15.2
- JWT
