# OOP-LegalActParser
Trzeba określić opcje: 

X						-> ścieżka do pliku,

-m X, --mode X 			-> dozwolone tryby to "show" i "tableOfContents"

OPCONALNE:

-s X, --section X 		-> można zawęzić spis treści do konkretnego działu lub wyświetlić jego treść,

-c X, --chapter X		-> można wyświetlić zawartość konkretneo rozdziału,

-t X, --title X			-> można wyświetlić zawartość konkretnego tytułu,

-a X, --article X		-> można wyświetlić zawartość konkretnego artykułu,

-P X, --paragraph X		-> można wyświetlić zawartość konkretnego paragrafu,

-p X, --point X			-> można wyświetlić zawartość konkretnego punktu,

-l X, --letter X		-> można wyświetlić zawartość konkretnej litery,

Wszystkie powyższe z wyjątkiem artykułu potrzebują bezwzględnej lub względnej do wybranego artkułu sieżki, spowodowane jest to tym, że tylko artykuły zawsze posiadają niezależne i unikalne indeksy
Reasumując:

wywołanie z argumentami: 

"sieżka -m show -a 21 -P 2 -p 2" jest poprawne

"sieżka -m show -P 2 -p 2" nie jest poprawne
						 
Program korzysta z biblioteki JCommander

