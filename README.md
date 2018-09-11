# FamilyApp

Aplikacja monolityczna pozwalajaca dodawnie Ojca, Dziecka, tworzenie rodzin, wyszukiwanie rodzin.
:8080/child
:8080/father
:8080/family

Aplikacja wystawia tez serwer Restowy:

:8080/rest/familyAdd
przyjmuje: FamilyDTO i dodaje do bazy --aktualnie jedyny sposob dodania rodziny do bazy

:8080/rest/child
przyjmuje: ChildDTO i dodaje go do listy dzieci w sesji

:8080/rest/father
przyjmuje: FatherDTO i dodaje ojca do sesji

Aktualnie child i father jest niepotrzebny ale w przyszlosci familyAdd zostanie zastapiony aby pobierac obiekty z sesji


###ChildDTO###
Long id;
String firstName;
String secondName;
String pesel;
String sex;
FamilyDTO familyDTO;
String date; -- Data wyznaczana po peselu
Map< String, String > sexMap = new HashMap<>();
        sexMap.put("man", "Man");
        sexMap.put("woman", "Woman");
        sexMap.put("other", "Other");
        
###FatherDTO###
Long id;
String firstName;
String secondName;
String pesel;
Date date;
List<FamilyDTO> familiesDTO;

###FamilyDTO###
Long id;
FatherDTO fatherDTO;
List<ChildDTO> childrenDTO;
