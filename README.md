# FamilyApp

Video:
https://drive.google.com/open?id=1emSIXHk8cr92SspJtoH9XIRPZ_C4SFp8

Aplikacja monolityczna pozwalajaca dodawnie Ojca, Dziecka, tworzenie rodzin, wyszukiwanie rodzin.
:8080/child
:8080/father
:8080/family

Aplikacja wystawia tez serwer Restowy:

:8080/rest/familyAdd
POST (FamilyDTO){return string;}
przyjmuje FamilyDTO i dodaje do bazy, zwraca String z komunikatem

:8080/rest/search
POST (ChildDTO){return List<ChildDTO>;} with FamilyDTO.id
przyjmuje ChildDTO, przeszukuje baze po parametrach, zwraca liste dzieci spelniajacych kryteria
        
:8080/res/search?id=
GET (int [childDTO.familyDTO.id]) {return familyDTO;}
przyjmuje parametr id z pola FamilyDTO.id w ChildDTO, zwaraca rodzine.


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
{{id: Long}}
{{firstName: String}}
{{secondName: String}}
{{pesel: String}}
{{date: Date}}
{{amiliesDTO: List<FamilyDTO>}}

###FamilyDTO###
{{id: Long}}
{{fatherDTO: FatherDTO}}
{{childrenDTO: List<ChildDTO>}}
