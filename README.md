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
{{id: Long}}
{{firstName: String}}
{{secondName: String}}
{{pesel: String}}
{{sex: String}}
{{familyDTO: FamilyDTO}}
{{date: String}} -- Data wyznaczana po peselu
{{sexMap: Options: Man , Woman , Other}}
        
###FatherDTO###
{{if: Long}}
{{firstName: String}}
{{secondName: String}}
{{pesel: String}}
{{date: Date}}
{{amiliesDTO: List<FamilyDTO>}}

###FamilyDTO###
{{id: Long}}
{{fatherDTO: FatherDTO}}
{{childrenDTO: List<ChildDTO>}}
