## 🚀 Guida all'Avvio

1. **Build del progetto:** `mvn clean install`
2. **Esecuzione:** `mvn javafx:run`

## Accesso al sistema

L'applicazione permette di accedere in due modi:

1. **Account predefiniti**

   È possibile usare utenti già presenti per testare l'app.

   👨‍🏫 **Docenti**
   | Username | Password |
   | -------- | -------- |
   | prof1 | 0000 |
   | prof2 | 0000 |
   | prof3 | 0000 |
   | prof4 | 0000 |
   | prof5 | 0000 |
   | prof6 | 0000 |
   | prof7 | 0000 |
   | prof8 | 0000 |
   | prof9 | 0000 |
   | prof10 | 0000 |

   🎓 **Studenti**
   | Username | Classe | Password |
   | -------- | ------ | -------- |
   | stud1 | 1A | 0000 |
   | stud2 | 1A |
   0000 | | stud3 | 1A | 0000 | | stud4 | 1A | 0000 | | stud5 | 1A | 0000 | | stud6 | 2A | 0000 | | stud7 | 2A |
   0000 | | stud8 | 2A | 0000 | | stud9 | 2A | 0000 | | stud10 | 2A | 0000 | ---

2. **Registrazione di un nuovo account**

   Se l’utente non è registrato, può creare un account tramite la pagina di registrazione.

   **Campi richiesti:**

- Username
- Nome
- Cognome
- Data di nascita
- Codice di iscrizione
- Password (con conferma)

**Codice di iscrizione**

- Fornito dalla segreteria scolastica
- Determina il **ruolo** e, per gli studenti, la **classe di appartenenza**

**Esempi illustrativi per l'app:**

- `001` → Studente classe 1A
- `002` → Studente classe 2A
- `003` → Studente classe 3A
- `004` → Studente classe 4A
- `005` → Studente classe 5A
- `006` → Docente

# 📘 SCHOOL MANAGER

## 🎓 Contesto Accademico

* **Corso di Studi:** Informatica (L-31)
* **Insegnamento:** Ingegneria del Software
* **Anno Accademico:** 2025/2026
* **Studenti:** Domenico Santagada / Andrea Mirarchi

## 📝 Descrizione

**SCHOOL MANAGER** è un'applicazione gestionale desktop sviluppata in Java e JavaFX progettata per digitalizzare e
ottimizzare i processi informativi e didattici all'interno di un istituto scolastico. L'app funge da registro
elettronico integrato, offrendo interfacce dedicate e sicure per due tipologie di utenti: docenti e studenti.

Lo scopo principale del sistema è quello di centralizzare la gestione della carriera scolastica in un unico ambiente
software robusto e intuitivo. Attraverso l'uso di architetture moderne e design pattern (come MVC, Singleton e Observer
e Strategy), l'applicazione permette ai docenti di monitorare l'andamento delle classi, registrare voti, presenze e note
disciplinari, e assegnare compiti digitali. Parallelamente, consente agli studenti di consultare in tempo reale i propri
risultati, giustificare le assenze e sottomettere elaborati in formato PDF, garantendo una comunicazione trasparente ed
efficiente tra le parti.

## 🌟 Moduli Funzionali Dettagliati

### 🏠 Area Comune

L’area comune comprende tutte le funzionalità accessibili **prima dell’accesso al sistema** ed è condivisa da entrambe
le tipologie di utenti: **Docenti** e **Studenti**.  
Questa sezione gestisce l’autenticazione, la registrazione e i meccanismi di sicurezza associati.

#### 🔐 Autenticazione (Login)

Dalla schermata iniziale, l’utente può accedere al sistema inserendo **username** e **password**.

- Possibilità di **commutare la visibilità della password** per facilitarne l’inserimento corretto.
- Validazione dei campi obbligatori.
- Visualizzazione di **messaggi di avviso specifici** in caso di:
    - credenziali errate
    - campi vuoti o incompleti

Il sistema garantisce un feedback immediato per migliorare l’esperienza utente e prevenire errori di accesso.

#### 📝 Registrazione Utente

Gli utenti non ancora registrati possono accedere a una sezione dedicata alla creazione di un nuovo account.

**Campi richiesti:**

- Username
- Nome
- Cognome
- Data di nascita
- Codice di iscrizione
- Password (con conferma)

**Validazione in tempo reale:**

- I campi *username*, *codice di iscrizione* e *password* utilizzano un sistema di feedback visivo immediato:
    - **verde** → dato valido o disponibile
    - **rosso** → errore di validazione o username già esistente

**Codice di Iscrizione:**

- Fornito dalla segreteria scolastica
- Determina automaticamente:
    - il **ruolo dell’utente** (Studente o Docente)
    - la **classe di appartenenza**
- Esempi:
    - `001` → Studente classe 1A
    - `002` → Studente classe 2A
    - ...
    - `006` → Docente

**Specializzazione Docente:**

- Se il codice di iscrizione identifica un docente, il sistema abilita dinamicamente un **menu a tendina** per la
  selezione della **materia di insegnamento**.

**Sicurezza delle credenziali:**

- Le password non vengono mai memorizzate in chiaro
- Vengono salvate nel database tramite **hashing con algoritmo BCrypt**, garantendo un elevato livello di sicurezza.

---

## 👨‍🏫 Area Docente

L’Area Docente rappresenta il cuore operativo dell’applicazione e fornisce all’insegnante un insieme completo di
strumenti per la gestione didattica, amministrativa e organizzativa della classe.

### 🏠 Home Docente

La Home Docente funge da **pannello di controllo principale** dell’insegnante.

**Informazioni di testata**

- Visualizzazione del **nominativo del docente** e della **materia di insegnamento**
- **ChoiceBox** per la selezione della classe corrente
- Al cambio di classe, il sistema aggiorna automaticamente il contesto dei dati visualizzati in tutte le sezioni
  dell’area docente

**Sezioni principali**
L’interfaccia è organizzata in una griglia di pulsanti intuitivi che consentono l’accesso rapido alle funzionalità:

- **Studenti** – gestione voti e note disciplinari
- **Compiti** – assegnazione di nuove attività didattiche
- **Presenze** – gestione del registro delle assenze
- **Consegne** – visualizzazione e download degli elaborati degli studenti
- **Esci** – terminazione della sessione e ritorno alla schermata di login

### 📋 Studenti

Questa sezione consente al docente di **monitorare l’andamento della classe**, inserire valutazioni e gestire la
disciplina.

**Visualizzazione e Monitoraggio**

- **Tabella Dati** con elenco degli studenti della classe selezionata, contenente:
    - nome e cognome
    - data dell’ultima valutazione
    - voto conseguito
- **Statistiche rapide** con indicatori dinamici:
    - numero totale di studenti
    - numero di studenti con valutazione sufficiente (voto ≥ 6)
    - numero di studenti con valutazione insufficiente (voto < 6)

**Azioni di Valutazione**

- **Inserimento / aggiornamento voti** su scala 0–10
- **Aggiunta di note disciplinari**, associate allo studente selezionato e registrate con data corrente

**Esportazione Dati – Pattern Strategy**

- Possibilità di esportare i dati della classe per finalità amministrative
- Implementazione tramite **Pattern Strategy**, che consente di selezionare dinamicamente il formato di esportazione (
  PDF o CSV), delegando la logica a classi dedicate

**Aggiornamento Dinamico – Pattern Observer**

- La pagina è costantemente sincronizzata con il database
- Grazie al **Pattern Observer**, il controller agisce come osservatore dei dati:
    - ogni modifica (voto o nota) genera una notifica
    - tabella e statistiche si aggiornano automaticamente senza ricaricare la pagina

### 📚 Compiti

Questa sezione è dedicata alla **creazione e assegnazione di attività didattiche** alla classe selezionata.

**Interfaccia di Inserimento**

- Area di testo per l’inserimento della descrizione dettagliata del compito o dell’argomento

**Gestione Automatica del Contesto**

- Recupero automatico di:
    - classe corrente
    - materia insegnata dal docente loggato
- Riduzione dell’inserimento manuale e prevenzione di errori formali

**Validazione e Feedback**

- Controllo del contenuto del campo di testo:
    - campo vuoto o contenente solo spazi → messaggio di avviso (`CAMPS_NOT_EMPTY`)
- Conferma visiva dell’operazione in caso di inserimento corretto
- Pulizia automatica del campo di input dopo l’assegnazione del compito

**Navigazione**

- Pulsante dedicato per tornare alla Home Docente, garantendo un flusso di navigazione fluido

### 📦 Consegne

Questa pagina permette al docente di **visualizzare, gestire e scaricare** gli elaborati consegnati dagli studenti.

**Visualizzazione Compiti**

- Recupero dinamico dei compiti associati alla classe selezionata
- Filtro automatico per mostrare solo i compiti creati dal docente loggato
- **Interfaccia a card (BorderPane)** con riepilogo di:
    - materia
    - descrizione
    - data di assegnazione

**Gestione Elaborati**

- Espansione dinamica delle card al click
- Visualizzazione delle consegne con:
    - nome dello studente
    - data di consegna
    - eventuale commento

**Sicurezza e Controllo Eliminazione**

- Menu contestuale per l’eliminazione dei compiti
- **Blocco dell’eliminazione** se sono presenti elaborati consegnati
- Messaggi di avviso per prevenire la perdita accidentale dei dati

**Download File**

- Download dei PDF tramite link interattivo
- Utilizzo di `FileChooser` per selezionare la cartella di destinazione
- Ricostruzione del file a partire dai byte memorizzati nel database e salvataggio tramite `FileOutputStream`

### 📅 Presenze

Questa sezione funge da **registro elettronico delle assenze** con una visualizzazione grafica avanzata.

**Calendario Interattivo**

- **GridPane dinamico** che genera un calendario mensile:
    - righe → studenti (ordinati alfabeticamente)
    - colonne → giorni del mese (28, 30 o 31)
- **Codice cromatico**:
    - Rosso (A) → assenza non giustificata
    - Blu (G) → assenza giustificata

**Gestione Temporale**

- Navigazione tra mesi tramite pulsanti dedicati
- Aggiornamento automatico della griglia ad ogni cambio mese
- Localizzazione automatica dei nomi dei mesi in lingua italiana

**Operazioni di Gestione**

- Inserimento assenze tramite:
    - selezione studente (ChoiceBox)
    - selezione data (DatePicker)
- Eliminazione rapida tramite menu contestuale sulle celle colorate

**Scelte Implementative**

- Utilizzo di una `HashMap` per associare ogni studente alla riga della griglia
- Celle implementate come `StackPane` creati a runtime
- Stile grafico gestito tramite CSS per garantire chiarezza e leggibilità

---

## 👨‍🎓 Area Studente

L’Area Studente è progettata per consentire allo studente la **consultazione completa e autonoma** delle informazioni
relative al proprio percorso scolastico.  
L’interfaccia riprende la struttura generale dell’area docente, ma è focalizzata esclusivamente sulla visualizzazione
dei dati personali e sull’interazione limitata alle funzionalità consentite.

### 🏠 Home Studente

La Home Studente rappresenta il punto di accesso principale alle funzionalità dedicate allo studente.

**Informazioni di testata**

- Visualizzazione del **nome completo dello studente**
- Visualizzazione della **classe di appartenenza**

**Funzionalità disponibili**

- **Andamento** – consultazione dei voti e delle medie
- **Compiti** – visualizzazione delle attività assegnate e gestione delle consegne
- **Note** – consultazione delle note disciplinari ricevute
- **Assenze** – monitoraggio e giustificazione delle assenze
- **Esci** – terminazione della sessione e logout dal sistema

### 📊 Andamento

Questa sezione fornisce una **visione completa e analitica del rendimento scolastico** dello studente, combinando
rappresentazioni grafiche e riepiloghi testuali.

**Rappresentazione Grafica**

- **Grafico a barre** che mostra l’andamento dei voti per ciascuna materia
- Colorazione dinamica:
    - verde → voto sufficiente (≥ 6)
    - rosso → voto insufficiente (< 6)

**Card delle Valutazioni**

- Lista verticale di card stilizzate, ciascuna contenente:
    - materia
    - voto
    - data della valutazione
- Colore di sfondo dinamico:
    - verde → sufficiente
    - rosso → insufficiente
    - grigio → valutazione in attesa o non ancora assegnata

**Riepilogo Statistico**

- **Calcolo automatico della media aritmetica** con precisione decimale
- Indicatori dinamici:
    - numero di sufficienze
    - numero di insufficienze
    - numero di voti in attesa

**Esportazione Dati – Pattern Strategy**

- Possibilità di esportare il **libretto personale dei voti**
- Implementazione tramite **Pattern Strategy**, con scelta del formato:
    - PDF
    - CSV

**Aggiornamento Dinamico – Pattern Observer**

- Il controller implementa l’interfaccia `DataObserver`
- Ogni aggiornamento del database relativo allo studente:
    - aggiorna automaticamente grafico, card e medie
    - non richiede il ricaricamento manuale della pagina

### 📚 Compiti

Questa sezione consente allo studente di **consultare i compiti assegnati** e gestire la consegna dei propri elaborati
digitali.

**Visualizzazione delle Attività**

- Recupero automatico dei compiti associati alla classe dello studente
- **Interfaccia a card** contenente:
    - materia
    - descrizione del compito
    - data di assegnazione
- Le card evidenziano l’interattività tramite variazione del cursore al passaggio del mouse

**Dettaglio del Compito**

- Selezionando un compito, lo studente accede a un’area dedicata alla consegna

**Sistema di Consegna**

- **Caricamento PDF** tramite `FileChooser`, con filtro per il solo formato PDF
- Campo di testo opzionale per l’inserimento di un **commento** destinato al docente
- Persistenza del file come array di byte nel database
- Creazione di un oggetto `ElaboratoCaricato` che associa:
    - studente
    - compito
    - data di consegna
    - file caricato

**Gestione Storico Consegne**

- Visualizzazione degli elaborati già consegnati
- Possibilità di:
    - riscaricare i file
    - eliminare una consegna per correggere eventuali errori

### 📅 Assenze

Questa sezione permette allo studente di **monitorare e regolarizzare le proprie assenze**.

**Visualizzazione**

- Tabella riassuntiva con elenco di tutte le assenze registrate
- Per ogni assenza vengono mostrati:
    - data
    - motivazione (se presente)
    - stato di giustificazione (Sì / No)

**Procedura di Giustificazione**

- Selezione dell’assenza dalla tabella
- Verifica preventiva:
    - assenza non già giustificata
    - presenza di una selezione valida
- Attivazione di un pannello dedicato con:
    - campo di testo obbligatorio per l’inserimento della motivazione
- In caso di campo vuoto, il sistema blocca l’invio e mostra un messaggio di avviso tramite `SceneHandler`

**Navigazione e Gestione Risorse**

- Al ritorno alla Home Studente, il controller si deregistra come osservatore dal database
- Possibilità di annullare la procedura di giustificazione in qualsiasi momento

### 📝 Note Disciplinari

Questa sezione consente allo studente di consultare i **provvedimenti disciplinari** ricevuti.

**Visualizzazione**

- Lista verticale dinamica delle note disciplinari
- Ogni nota è rappresentata tramite una **card grafica (BorderPane)** contenente:
    - nominativo del docente
    - descrizione del provvedimento
    - data di emissione

**Generazione Dinamica**

- Le note vengono generate programmaticamente dal controller
- L’interfaccia supporta un numero variabile di record senza limiti strutturali

**Integrità dei Dati**

- All’inizializzazione, il sistema identifica lo studente tramite `SceneHandler`
- Recupero esclusivo dei dati di pertinenza dello studente loggato
- Visualizzazione in testata del nome completo e della classe di appartenenza

**Navigazione**

- Sezione a sola consultazione
- Pulsante dedicato per il ritorno alla Home Studente

---

## 🧩 Architettura e Design Pattern

L'applicazione implementa i principali pattern di ingegneria del software per massimizzare la modularità, la
manutenibilità e la scalabilità del sistema.

### 1. Architettura Model-View-Controller (MVC)

Il sistema adotta l'architettura **MVC** per separare nettamente le responsabilità logiche e grafiche:

* **Model**: Rappresenta le entità di dominio e i dati dell'applicazione (es. `User`, `Assenza`, `CompitoAssegnato`).
* **View**: Definita attraverso file **FXML** per il layout e fogli di stile **CSS** per l'estetica, garantendo
  un'interfaccia utente dichiarativa e disaccoppiata dalla logica.
* **Controller**: Gestisce l'interazione dell'utente, manipolando il modello e aggiornando la vista (
  es. `LoginController`, `AssenzeController`).

### 2. Singleton Pattern

Il pattern **Singleton** è stato utilizzato per centralizzare la gestione di risorse critiche, garantendo che esista una
sola istanza globale per l'intero ciclo di vita dell'applicazione:

* **Database**: Centralizza l'accesso alla logica dei DAO e gestisce la notifica degli osservatori.
* **DatabaseConnection**: Gestisce la connessione fisica al database SQLite, ottimizzando l'uso delle risorse.
* **SceneHandler**: Coordina il caricamento delle interfacce JavaFX e il passaggio tra le diverse scene (login, home,
  registri), mantenendo traccia dello stato dell'utente loggato.

### 3. Data Access Object (DAO) Pattern

Per isolare la logica di persistenza dei dati dal resto della business logic, è stato implementato il pattern **DAO**.
Ogni entità principale dispone di una classe dedicata per le operazioni CRUD sul database SQLite:

* **UserDAO**: Gestisce l'autenticazione, la registrazione e il recupero delle informazioni anagrafiche.
* **VotiDAO**, **AssenzeDAO**, **NoteDAO**, **CompitiDAO**: Gestiscono la persistenza dei dati specifici del registro
  elettronico, isolando le query SQL all'interno di metodi specifici delegati dalla classe `Database`.

### 4. Observer Pattern

Fondamentale per garantire la sincronizzazione in tempo reale dell'interfaccia utente. La classe `Database` agisce come
**Soggetto Concreto** (`ObservableSubject`), mentre i controller delle dashboard (es. `AndamentoController`)
implementano l'interfaccia `DataObserver`.

* Quando un docente inserisce un nuovo voto o una nota, il database notifica automaticamente tutti i controller
  registrati.
* Questo permette alla vista dello studente di aggiornare grafici e tabelle istantaneamente, senza necessità di refresh
  manuale della pagina.

### 5. Strategy Pattern

Applicato nel modulo di esportazione per consentire al sistema di generare report in formati differenti (PDF o CSV) in
modo flessibile e trasparente:

* L'interfaccia `ExportStrategy` definisce il contratto comune per l'esportazione dei dati.
* Le strategie concrete (es. `PDFClassExportStrategy`, `CSVClassExportStrategy`) implementano la logica di formattazione
  specifica per ogni tipo di file.
* La classe `ExportContext` permette di selezionare e iniettare la strategia corretta a runtime in base alla scelta
  dell'utente.

### 6. Persistenza e Sicurezza

* **Persistenza**: Il sistema utilizza **SQLite**, un database relazionale leggero che memorizza i dati localmente,
  semplificando la distribuzione dell'applicazione.
* **Sicurezza**: La protezione delle credenziali è garantita dal servizio `BCryptService`, che esegue l'hashing delle
  password prima del salvataggio, rendendo il sistema sicuro contro l'accesso non autorizzato ai dati in chiaro.
