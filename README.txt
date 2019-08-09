FOR compiling the java file please complile the bank first, then Customer and then money class. because money is dependent on customer and customer dependent on bank.

javac bank.java
javac Customer.java(NOTE:the c in the customer is in the capital letter(CAPS), when i tried to change it was giving errors)
javac money.java

after compliling
run by:-
java money

or 
=======================================================
compile the whole java class together:

javac money.java Customer.java bank.java

then run by:

java money
============================================================
for Erlang:-
you may or may not receive warning while compiling the code it is because i had developed it on previous version of erlang, when i later upgraded to latest version 10.4, it was giving warning
Please ignore that.


