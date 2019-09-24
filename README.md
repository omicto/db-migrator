# db-migrator
Proyecto de clase que migra el esquema de ejemplo de Oracle `HR` a SQL Server. Incluyendo tablas, constraints y datos.
## Pre-requisitos
* Java JDK 8 o superior
* Maven
* Instalaciones de Oracle XE 11g y SQL Server 2017

## Instalación
1. Descargue los archivos del driver JDBC de SQL Server. [Link de descarga](https://www.microsoft.com/es-mx/download/details.aspx?id=11774).
2. Descomprima en el lugar de su preferencia, pero tome nota de la ruta en que lo hace, esto sera necesario mas adelante.
    * En particular, nos interesa el archivo localizado en `\sqljdbc_6.0\enu\auth\x64` (o `x86` si el sistema es de 32 bits).
3. Añada el archivo `ojdbc6.jar` a su repositorio local de Maven siguiendo las instrucciones [especificadas aquí](https://maven.apache.org/guides/mini/guide-3rd-party-jars-local.html). Usualmente esta localizado en la ruta `<Su instalacion de Oracle>\app\oracle\product\11.2.0\server\jdbc\lib`.
4. Clone el repositorio.
5. Ejecute `mvn clean package`

### Configuracion de Microsoft SQL Server
Por defecto, SQL Server tiene deshabilitada la opción de conexión por TCP/IP.
Siga los pasos especificados [aquí](https://www.ibm.com/support/knowledgecenter/es/SSB2MV_7.1.2/com.ibm.rational.buildforge.doc/topics/preinst_db_mssql_tcpip_enable.html) para solucionarlo.

### Configuración adicional
Asegurese de configurar los datos especificos a su instalacion de Oracle y SQL Server en el archivo `application.properties` del proyecto antes de compilarlo.

## Uso
Para ejecutar el proyecto, es necesario especificar el nombre que se le dará a la base de datos de destino en SQL Server
```cmd
java -jar -Djava.library.path=<LA RUTA MENCIONADA EN EL PASO 2> target/db-migrator-1.jar <NOMBRE DE LA BASE DE DATOS DE DESTINO>
```
Ejemplo
```cmd
java -jar -Djava.library.path=C:/mssql-auth target/db-migrator-1.jar NEW_HR
```
