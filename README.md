## Author

Juan Pablo Poveda Cañon

# Taller 4 - Servidor Concurrente

## Prerequisitos

Maven: Automatiza y estandariza el flujo de vida de la construcción de software.

Git: Administrador descentralizado de configuraciones.

## Instalación

Para instalar el proyecto maven se usa el siguiente comando:

```
mvn clean install
```

## Ejecución

Para ejecutar el proyecto maven se el siguiente comando

```
 mvn -e exec:java -Dexec.mainClass=edu.escuelaing.arsw.ase.app.WebServer.java
```

Y probamos en varios browsers la URL:

[http://localhost:35000/index.html](http://localhost:35000/index.html)

![ServerTest](https://github.com/juancanon1725/Taller4_ARSW/assets/98672541/23c642f0-8add-4aec-aceb-15c01ad5018d)

Y podemos ver que el servidor recibe diferentes request de los browsers concurrentemente.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details
