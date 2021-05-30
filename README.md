# Challenge MeLi

Aplicación construida para que suministrada un array de cadenas 
(compuestas por las letras A,T,G,C, estas cadenas hacen similitud a una cadena de DNA y que se puede expresar como una matriz de NxN) . Dicha cadena pasara por un analizador que determina si cumple con DNA mutante o humano.

Para realizar esta validación es necesario conformar una matriz que al ser valida por filas , columnas y de manera diagonal , verificar si existen secuencias de 4 letras iguales seguidas.
Si se encuentra mas de 1 secuencia con 4 letras iguales seguidas , el DNA se cataloga como Mutante de lo contrario se Cataloga como Humano.
El aplicativo presenta como resultado los siguientes datos:

● HTTP 200 para DNA mutante

● HTTP 403 para DNA humano.

● Servicio de estadistica (Conteo de mutantes y de humanos y una proporcion).

## Comenzando 🚀

Estas instrucciones te permitirán obtener una copia del proyecto en funcionamiento en tu máquina local para propósitos de desarrollo y pruebas.

```
git clone https://github.com/paguerrero1122/MutantesService.git
```


### Pre-requisitos 📋

Las herramientas necesarias para ejecutar el proyecto son:

● JDK 1.8

● Docker

● Maven



### Instalación 🔧

Se debera arrancar un contenedor con imagen Redis que sera la base de datos para la persistencia de la aplicacion. ejecutar el siguiente comando:

```
docker run -d --name redis-server -p 6379:6379 redis
```
Una vez clonado este repositorio es necesario construir un archivo .jar que contiene la aplicación.
Para ello entrar a la carpeta raiz del proyecto donde se encuentra el archivo pom.xml y ejecutar el siguiente comando

```
mvn clean install
```

Es momento de lanzar a la aplicación , el comando anterior debio generar la carpetar "target" y dento el archivo ChallengeMutantes-0.0.1-SNAPSHOT.

entrar a dicha carpeta y ejecutar el siguiente comando en consola.

```
java -jar ChallengeMutantes-0.0.1-SNAPSHOT.jar 
```
De esta forma la aplicacion subira y expondra los servicios Rest de manera local para interactuar.

## Ejecutando las pruebas ⚙️

Una vez terminada la instalaciòn es hora de realizar la prueba.
Abrir PostMan o cualquier aplicación que permita consumir servicios Rest

● Prueba 1 Analizar DNA Humano: la petición se realiza mediante el servicio POST localhost:8080/serviceMutants/mutant , como se muestra a continuación.

https://raw.githubusercontent.com/paguerrero1122/MutantesService/master/Prueba1.png

● Prueba 2 Analizar DNA Mutante: la petición se realiza mediante el servicio POST localhost:8080/serviceMutants/mutant , como se muestra a continuación.

https://raw.githubusercontent.com/paguerrero1122/MutantesService/master/Prueba2.png

● Prueba 3 Obtener estadisticas: la petición se realiza mediante el servicio POST localhost:8080/serviceMutants/mutant , como se muestra a continuación.

https://raw.githubusercontent.com/paguerrero1122/MutantesService/master/Prueba3.png

La aplicación tambien se encuentra desplegada en AWS por lo cual si desea realizar las pruebas contra ese servidor cambie localhost por 18.117.81.229

http://18.117.81.229:8080/serviceMutants/mutan

http://18.117.81.229:8080/serviceMutants/stats

## Construido con 🛠️

* [Spring boot + Java 1.8] Tomcat Auto embebido
* [Maven]
* [Docker]
* [Redis] para acceso rapido a BD
* [JUnit] pruebas unitarias

## Autores ✒️

* **Pedro Alejandro Guerrero** 
