# Modification du code existant
## Orientation
Dans la classe `Orientation`, la propriété `keyCode` a été ajoutée.
Elle contient la touche du clavier associée à l'orientation. Par exemple `Keyboard.DOWN` pour `Orientation.DOWN`

Cette modification permet d'éviter de répéter le même code 4 fois lorsque l'on veut vérifier si une touche est appuyée pour se déplacer.
```
for (Orientation orientation : Orientation.values())
    getKeyboard().get(orientation.getKeyCode()).isDown();
```

## setIsPassingDoor, passedDoor
Cette modification est nécessaire pour ne pas répéter du code dans l'extension Portal décrite plus loin.

Les méthodes `setIsPassingDoor` et `passedDoor` ont été remplacées par une méthode plus générique `getDestination`.
Elle retourne un objet implémentant l'interface `Destination` ou `null` si aucun déplacement n'est souhaité par l'entité.

Les classes pouvant être déplacées implémentent l'interface `Teleportable`.

La méthode update d'Enigme vérifie donc maintenant si les objets `Teleportable` (et plus uniquement le player) souhaitent un déplacement et les déplacent.
La liste des objets teleportables est stockée en propriété de la classe `Area`.

## Depth correction
Certaines fois, le joueur était affiché sous les PressureSwitch, pour corriger ça, à la création du Sprite du joueur,
le paramètre depthCorrection est utilisé. 

# Extensions

## Sprites animées (4pts)
Classe : `ch.epfl.cs107.play.game.areagame.actor.AnimatedSprite`

Classe permettant la gestion de plusieurs sprites associés à un acteur.
Elle a 2 constructeurs :
- un qui prend en paramètre une image qui contenant tous les sprites et pouvant avoir des orientations
(utilisé pour `EnigmePlayer` et `TalkingActor`),
- un autre qui prend en paramètre un tableau d'images qui vont être affichées successivement, pas d'orientation possible
(utilisé pour `Torch`)

A chaque update, il faut appeler la méthode update d'AnimatedSprite qui va passer à l'image suivante si nécessaire
en fonction du temps.

Lorsque l'acteur change d'orientation, il faut appeler setOrientation.

setSpriteIndex permet de forcer l'affichage d'une image spécifique de la séquence
(utile pour afficher le personnage debout immobile à la fin du déplacement).

Il suffit d'appeler la méthode draw pour dessiner la sprite appropriée selon d'état (orientation et index de l'animation).


## Portal
_estimation reçue: 5-6pts.
Après cette estimation, nous avons encore ajouté la possibilité de faire traverser les Portals aux PushableRocks._

Cette extension est inspirée du jeu Portal, le joueur a deux portails qu'il peut poser où il le souhaite
(dans la même Area ou une différente).
- Il pourra ensuite rentrer dans un portail (interaction de contact) et il resortira de l'autre.
- Un portail peut être replacé et l'ancien disparait, il communiquera toujours avec son portail associé.
- Les roches poussables (extension décrite plus loin) peuvent aussi traverser ces portails. 

Comme la notion de Portail est similaire au passage de portes, c'est pourquoi le code du passage de porte a été modifié
pour utiliser fonctionner avec les deux.

Classes / interfaces :
- `ch.epfl.cs107.play.game.enigme.actor.Portal`
- `ch.epfl.cs107.play.game.areagame.actor.Teleportable`
- `ch.epfl.cs107.play.game.areagame.actor.Destination`

La classe Portal a uniquement un constructeur privé car les portails doivent être créés par paire.
Il y a donc une méthode qui crée deux portails associés: `createPortalPair`

Si on essaie de rentrer dans un portail alors que le deuxième n'est pas passé, une erreur s'affiche un Dialog (extension décrite plus loin).

#### Spécificité pour le placement et déplacement d'un portail
Pour le déplacer, il faut le retirer de l'ancienne cellule puis l'ajouter sur la nouvelle.
Pour éviter tout problème, ce déplacement se fait en 2 updates. Lors du premier appel à `place` on unregister d'acteur et retourne false.
Le player recevant false sait qu'il devra rappeler la méthode au prochain update. La deuxième fois, la méthode appelera registerActor.

#### getDestinationCoordinates(Teleportable)
Lorsque le portail est placé, il a une orientation (face au player qui l'a posé) quand on passe, on veut que l'actor resorte
de ce côté du portail. Cette méthode donne donc la cellule sur laquelle ressport l'acteur.

Dans le cas où cette cellule est déjà occupée, on vérifie les autres cellules adjacentes et si aucune n'est trouvée,
on le laisse sur le portail d'arrivée et il sera retéléporté dans l'autre sens.

#### teleport(Teleportable)
On assigne simplement la destination au Teleportable et la méthode update d'Enigme va se charger du déplacement.

#### Enigme.update()
Pour tous les Teleportables de l'area :
- Si c'est un EnigmePlayer, on utilise ses méthodes spécifiques pour le faire quitter et entrer dans la nouvelle aire et
on défini l'Area de destination comme Area courante.
- Sinon on unregister l'acteur de l'ancienne Area, passe à la nouvelle Area (pour garantir que tout soit mis à jour correctement),
puis on register l'acteur et repasse à l'ancienne Area où le joueur se trouve.


## PushableRock (4pts)
Classes / interfaces :
- `ch.epfl.cs107.play.game.enigme.actor.PushableRock`
- `ch.epfl.cs107.play.game.enigme.actor.Pushable`

PushableRock est une `MovableAreaEntity` qui est :
- `Interactable` pour que le joueur puisse interagir à distance pour pousser le rocher
- `Interactor` pour qu'il puisse activer des PressurePlate / PressureSwitch
- `Teleportable` pour qu'il puisse passer à travers les portes et portails.

Une méthode a été ajoutée dans `EnigmePlayerHandler` qui va simplement appeler la méthode push.
Ainsi lorsqu'on appuie sur la touche L pour interagire, le rocher va être poussé.

Pour une interaction plus naturelle, on aimerait pouvoir marcher avec le personnage contre le rocher et qu'il se déplace tout seul.
On peut faire ça en modifiant la méthode canEnter pour pousser le rocker qui se trouve dans la cellule que le joueur veur entrer.

Avec ce qui a été fait dans l'extension Portal, il est possible de leur faire traverser des Portals et Doors. 

## Dialog (3-5pts)
Classe :
- `ch.epfl.cs107.play.game.enigme.actor.TalkingActor`

Acteur avant une AnimatedSprite pour être orientable, ainsi il se tourne vers le player qui interagit avec.
Lorsque le player interagit à distance (touche L), un dialog s'affiche. A la prochaine interaction, le texte suivant s'affiche
et se ferme quand il n'y a plus de texte. 

## Pause (2pts)
Classe :
- `ch.epfl.cs107.play.game.enigme.area.Pause`

Dans la méthode update de Enigme, on vérifie si la touche P est appuyée, si c'est le cas, on passe à l'Area Pause.
En appuyant une deuxième fois sur P, on revient à l'ancienne Area.

Les méthodes suspend et resume sont appelées automatiquement dans setCurrentArea.