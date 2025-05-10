# Ventacar - Projecte WEB

Ventacar es una aplicació web per a la compra i reserva de cotxes. Ofereix una plataforma sencilla i accessible on els usuaris poden registrarse o iniciar sessió per explorar els vehicles, veure detalls específics i realitzar compraes o reserves. Està disenyada per a clients com per administradors qui gestionaran els vehicles disponibles.


## **Funcionalitats bàsiques**

Registre i autentificacio d'usuaris(login/logout)

Llistat de vehicles amb filtres de busqueda.

Visualització dels detalls del cotxe(model,preu,caracteristiques)

Reserva o compra del vehicle.

Generació de factures(pdf) personalitzades per a l'usuari.

Enviaments de correus amb factura integrada tan per compra com per reserves de cotxes.


## **Tecnologies i llenguatges de programació**

*Version*: JDK22, Jakarta EE 9

*Frontend*: HTML, CSS, JavaScript

*Backend*: Java, Spring Boot

*Bases de dades*: PostgreSQL

*Serveis web*: RESTful API

*Servidor web*: Tomcat 10.1

*Editor*: IntelliJ IDEA

*Seguretat*:Spring Security(roles User i Admin)

*Pdf i correu*: Itext i mailtrap. Mailsersend i SMTP (Per a l'enviament de correus als clients
)


## **Diseny de plantilles UI**

link a diseny figma: https://www.figma.com/design/6uRpn3rJaPPZN0Za9UJrs3/tea3?node-id=0-1&p=f&t=uwbky68QuhGPjc9K-0

Interfas dividida entre: index.html, login.html, resultats.html, nosatres.ht,l i detall.html


## **Diseny de la BBDD**

![Diagrama de base de datos](https://github.com/elianiubo/ventacar/blob/main/src/main/assets/DBrelational.jpeg?raw=true)

Taules: roles,user_roles, users, vehiculos

### Columnes de cada taula:

roles: id,name

user_roles: user_id, role_id

users:id,apellidos, codigo_postal, email, decha_nacimiento, nombre, password, telefono

vehiculos: id,marca,tipo,precio,imagen,descripcion

### Endpoints:

- GET /api/vehículos/buscar
- GET /api/vehículos/marca{marca}
- GET /api/vehículos/tipo{tipo}
- GET /api/vehículos/precio

## **Documentacio del codi font i Comentaris JavaDoc**

Estructura organitzada per (Config, Model, Controller, Service, Repository)

Rutes REST definides als controlladors
