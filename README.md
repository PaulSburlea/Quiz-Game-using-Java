[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-24ddc0f5d75046c5622901739e7c5dd533143b0c8e959d652212380cedb1ea36.svg)](https://classroom.github.com/a/DqvRgdrh)
# Quiz
### Sburlea Benjamin-Paul-Angel

## Descriere
Aplicația este compusă din mai multe întrebări cu variante multiple de răspuns (grile) sau întrebări cu răspuns scurt bazate pe cunoștințe generale, sport, tehnologie, etc. Jucătorii trebuie sa selecteze răspunsul corect și pot avansa la nivelul următor. La fiecare răspuns corect jucătorul primește un număr de puncte. Aplicația va păstra un clasament al jucatorilor.

## Obiective

* Funcționalitatea corectă a întrebărilor și răspunsurilor
* Dezvoltarea unei aplicații funcționale
* Diversitatea funcționalităților
    - Timer pentru fiecare întrebare
    - Diverse moduri de joc (mod normal, mod cu timp limitat, intreaba un prieten...)
    - Nivele de dificultate
* Gestionarea scorului și clasamentelor

## Arhitectura
* Interfața utilizator (UI)
* Logica aplicației
* Bază de date

## Clase și relațiile dintre ele

* Clasa QuizGame:
    - Această clasă gestionează jocul în sine și coordonează toate celelalte clase și funcționalități.
    - Are o relație cu clasa Player pentru a urmări scorurile jucătorilor.
    - Are o relație cu clasa QuestionBank pentru a obține întrebări pentru joc.
* Clasa Player:
    - Această clasă stochează informații despre jucători, cum ar fi numele și scorurile.
    - Are o relație cu clasa Authentication pentru autentificarea jucătorilor.
* Clasa Question:
    - Această clasă reprezintă o întrebare specifică din joc, care include textul întrebării și răspunsurile corecte și greșite.
    - Este parte a clasei QuestionBank și poate fi accesată de QuizGame.
* Clasa QuestionBank:
    - Această clasă gestionează baza de date a întrebărilor.
    - Are o relație cu clasa Question pentru a stoca și gestiona întrebările.
* Clasa Authentication:
    - Această clasă se ocupă de autentificarea și gestionarea utilizatorilor.
    - Are o relație cu clasa Player pentru a gestiona profilurile de jucători.
* Clasa Scoreboard:
    - Această clasă se ocupă de gestionarea scorurilor și a clasamentelor jucătorilor.
    - Are o relație cu clasa Player pentru a accesa și actualiza scorurile jucătorilor.
* Clasa GameTimer:
    - Această clasă este utilizată pentru a gestiona cronometrul în timpul jocului.
    - Are o relație cu clasa QuizGame pentru a gestiona timpul jocului.

## Functionalitati/Exemple utilizare
* Functionalitati:
    - Timer pentru fiecare întrebare
    - Diverse moduri de joc (mod normal, mod cu timp limitat, intreaba un prieten...)
    - Nivele de dificultate



### Resurse
Markdown Guide, [Online] Available: https://www.markdownguide.org/basic-syntax/ [accesed: Mar 14, 1706]
