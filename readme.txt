Meesages:

REGISTER tamar 123 02/01/1996

LOGIN tamar 123 1

REGISTER tomer 321 10/09/1997

LOGIN tomer 321 1

REGISTER ido 456 25/11/1995

LOGIN ido 456 1

LOGOUT

FOLLOW 0 tomer

FOLLOW 1 tomer

POST Hello World

PM tamar Hello Tamar

LOGSTAT

STAT tomer|tamar

BLOCK tomer


Java Run:

mvn compile

mvn exec:java -Dexec.mainClass="bgu.spl.net.srv.TPCMain" -Dexec.args="7777"

mvn exec:java -Dexec.mainClass="bgu.spl.net.srv.ReactorMain" -Dexec.args="7777 3"

C++ Run:

make

./bin/BGSclient 127.0.0.1 7777


Filtered Words:
/home/spl/Assignment3/Server/src/main/java/bgu/spl/net/api/FilteredWords
