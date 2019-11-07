# java-RMI

## Data-Security second assingment.
To run it:
1. compile everything with ` javac -d build -cp *:. *.java`.
2. Run the server: `cd` into "build" folder and `java -cp *:. ApplicationServer`.
3. Run the client: `cd` into "build" folder and `java -cp *:. Client`.

## To populate a MySQL dB:
1. `cd /$HOME/java-RMI/extraFiles/sql_database`.
2. `sudo mysql -u root -p data_security < data.sql`.

### Beware that:
1. The dB expects that admin user is `dataUser` and password: `password`.
2. It is expected that the dB is hosted in `localhost` and its called `data_security`.
