**OTP-2 RYHMÄ 4 - K2021 Varastonhallintasovellus** (Henrik Aho, Nikke Tikka, Raoul Osman ja Pauli Vuolle-Apiala)


**Projekti lyhyesti:**

Projekti on toteutettu käyttämällä Eclipse IDE-ympäristöä. Projekti on rakennettu käyttäen Apache Maven-projektinhallinta – ja kokoamistyökalun avulla. Kolmansien osapuolten kirjastot määritellään projektin src-hakemistossa olevassa POM.xml tiedostossa. Koska projekti on toteutettu ketteränohjelmistokehitysten (Scrum) periaatteiden mukaisesti, on jatkuva integraatio tärkeä osa projektia. Automatisoitu kokoaminen, testaus ja integrointi tapahtuu Jenkins-palvelimella. Projektin tehtävienhallinta on toteutettu Nektionilla.
Sovelluksen käyttää MariaDB:n SQL-tietokantaa joka pyörii Metropolian educloud-palvelimella.

**Projektin asennusohjeet:**

**Käyttäjätunnus: otpope**

**Salasana: otpope**


**Käyttäessäsi IDE-ympäristöä.**

Projektin voi käynnistää suoraan Mavenillä.

        1.      Run As
        2.	Maven build...
        3.	Goals kohtaan: javafx:run

Tai...

Projektin Main-luokka löytyy src-hakemistosta view-paketin alta. Tiedoston nimi on Main.java, projektin voi laukaista suoraan tästä tiedostosta.


**JAR-tiedoston luominen projektista**

        1. Run As
        2. Maven build...
        3. Goals kohtaan: assembly:single
        4. JAR-tiedosto tulee projektin target-hakemistoon
          Esim. C:\Users\User_name\git\otp1r4\com.vogella.maven.eclipse\target





**Huomioita.**

- Kirjautumisen jälkeen ohjemassa on pieni viive avautumisessa.
- Tietokannan taulut käyttävät automaattista päivitystä
- Mikäli ympäristösi ei pyöritä javaFX:ää, muista tarkastaa Eclipsen virtuaalikoneen argumentit. Varsinkin jos JDK on uudempi kuin 8.0

                - Run gonfigurations =>
                - Arguments => VM arguments kohtaa:
                - --module-path "hakemiston_polku\javafx-sdk-15\lib" --add-modules javafx.controls,javafx.fxml



**Generate Javadocs**

Run as => Maven Build...
Goals: javadoc:javadoc

or MVN javadoc:javadoc
