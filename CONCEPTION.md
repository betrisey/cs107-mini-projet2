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

Spécificité pour le déplacement d'un portail :  

## PushableRock (4pts)

## Dialog (4pts)

## Pause (2pts)