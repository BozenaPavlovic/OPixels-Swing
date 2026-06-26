# OPixels-Swing

Java Swing desktop aplikacija s mini-igrama, korisnickim racunima i lokalnom serijalizacijom podataka.

## Pokretanje

```powershell
cd "C:\Users\Bozena\Desktop\OOP JAVA\opixels-swing"
javac -d out (Get-ChildItem -Path src -Recurse -Filter *.java | ForEach-Object { $_.FullName })
java -cp out opixels.OPixelsApp
```

## Struktura

- `src/opixels/model/` - User, statistika
- `src/opixels/storage/` - ucitavanje/spremanje `users.ser`, `stats.ser`, `global.ser`
- `src/opixels/ui/` - Swing ekrani i navigacija
- `src/opixels/ui/games/` - mini-igre

## Funkcionalnosti

- Prijava i registracija
- Glavni izbornik s igrama i statistikom
- Flip Coin, Speed Clicker, Rock Paper Scissors, Guess the Number
- My Stats i Global statistika
- JMenuBar (Izlaz) i JToolBar (Natrag, Stats, korisnik, Odjava)
