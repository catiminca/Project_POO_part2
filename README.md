Minca Ecaterina-324CAb

Continuarea acestui proiect a constat in adaugarea unor noi functionatiltati.
Cum am specificat de la prima etapa, ma folosesc de clasele din pachetul input
pentru a citi datele din fisiere.

In clasa AllActions imi gestionez functionaliatile in functie de tipul acesteia.
Am pastrat ca la etapa trecuta ca actiunile sa se faca in clasa CurrentPage,
inclusiv cele noi. Cele adaugate in etapa 2 au fost:
- database, care a constat in adaugarea unui nou film sau stergerea unuia deja existent
- subscribe, care reprezenta subscribtia la un anumit gen de film
- back, in care trebuia sa se mearga pe pagina anterioara

La final, in cazul in care utilizatorul era unul premium, trebuia sa i se faca o 
recomandare. Din filmele apreciate de acesta, trebuiau sortate genurile cele mai
apreciate si sortate filmele nevazute dupa genuri. 

Am folosit de la prima etapa design pattern-ulrile Singleton la clasa DatabaseInput
si un strategy la sortarea dupa rating si durata, folosind o intefata comuna pentru 
acestea. La a 2a etapa am mai adaugat Memento pentru a putea retine cum se schimba
paginile si pentru a putea merge pe pagina anterioara si Visitor pentru a putea face
cealta sortare necesara, cea dupa actori si genul de film, folosind interfetele Visitor
si Visitable.

Feedback: Mi-a placut mult etapa a2a si tot proiectul si chiar simt ca dupa aceste
teme am putut intelege mai bine aceasta materie.
