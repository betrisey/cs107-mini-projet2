# Comment lancer le jeu
Dans le fichier Play.java, décommenter la ligne appropriée pour commencer le jeu souhaite
```
final Game game = new Enigme();
final Game game = new Demo1();
final Game game = new Demo2();
``` 

# Contrôle du jeu
#### Général
- Utiliser les flèches du clavier pour se déplacer
- Appuyer sur la touche `L` pour interagir avec les objets : torches, switches, pommes, etc...
- Pour parler avec les personnages, il faut appuyer sur la touche `L`, puis rappuyer pour afficher la suite.
La boîte de dialogue se ferme à la fin du message en appuyant encore sur `L`. 

#### Rocher
- En marchant contre un rocher, on arrive à le pousser
- Il est aussi possible d'interagir à distance avec le rocher pour le pousser (touche `L`)
- **Attention à ne pas bloquer le rocher contre le mur car on peut uniquement le pousser mais pas le tirer.**
(Indice: Utiliser les portails pour faire traverser le labyrinthe au rocher)

#### Portail
- Appuyer sur la touche `J` pour créer un portail orange et `K` pour un portail bleu
- Le rocher peut passer à travers les portes et les portails 
- Pour se téléporter, il faut marcher sur un des portails après avoir placé les deux
- Les portails peuvent être placés dans des Area différentes et communiquent toujours

#### Pause
- Appuyer sur la touche `P` pour mettre en pause le jeu, appuyer une 2ème fois pour continuer