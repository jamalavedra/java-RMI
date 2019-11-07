# java-RMI

## Data-Security second assingment.
To run it:
1. compile everything with ` javac -d build -cp *:. *.java`.
2. Run the server `java -cp *:. ApplicationServer`.
3. Run the client `java -cp *:. Client`.

## To populate a MySQL dB:
`sudo mysql -u root -p data_security < data.sql`.

###Beware that:
1. The dB expects that admin user is `dataUser` and password: `password`.
2. It is expected that the dB is hosted in `localhost` and its called `data_security`.
