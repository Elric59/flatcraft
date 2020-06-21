# Flatcraft by DLB

Le but de cet exercice est de proposer une implémentation objet du jeu "Flatcraft".

L'objectif pédagogique de l'exercice est d'utiliser au maximum les bonnes pratiques de programmation en Java (vérifiées par Sonar), d'utiliser les mécanismes disponibles dans l'API Java (AWT/Swing), et de mettre en application des patrons de conceptions (nouveaux ou ceux vus en S3).

La taille de l'espace de jeu est fixée dans la classe principale. Elle est étudiée pour fonctionner au minimum sur l'écran d'un Macbook Air 13" (1440x900 pixels).

## Les contrôles

Le personnage peut se déplacer de deux façons :

- normalement, en creusant devant lui si des blocs sont présents en utilisant la touche `CTRL`
- mode passe-muraille, qui permet de se déplacer sur les cellules pleines, en utilisant la `barre d'espace`.

Le personnage se deplace dans une direction marquée par un curseur. Il suffit d'utiliser `les flêches du pavé numérique` pour changer de direction.

Au départ du jeu, le personnage dispose de deux outils : une hâche en bois et une pioche en bois. Pour changer d'outil, on utilise `MAJ + flêche droite/gauche`.

**Comme proposé en cours, il n'y a pas de différence fondamentale entre utiliser un outil ou une ressource : s'il l'on dispose d'un outil, la touche `CTRL` permet de creuser ; si l'on dispose d'une ressource, la touche `CTRL` permet de la poser.**

## Fonctionnalités spécifiques

Lorsque l'on pose une resource sur la case ou se trouve le personnage, le personnage est déplacé d'une case dans la direction opposée.

## Table de craft

La table de craft ne connait que quelques règles, qui permettent d'obtenir des pioches et haches en bois, pierre et fer.

Ajouter de nouvelles règles n'est pas difficile. Il faudrait idéalement qu'elles soient disponibles dans un fichier texte. Ce n'est pas le cas actuellement.

## Four

Le four permet de transformer un minerai en lingot, si l'on dispose de combustible. Il n'y a pas de différences entre les types de combustibles.

**Remarque : le code actuel ne limite pas le dépôt des ressources aux seuls minerais et aux combustibles, alors que cela devrait être le cas. Il y a un souci au niveau de l'implémentation qui doit être corrigé.**

## Jouabilité

La touche `CTRL` n'est pas idéale pour creuser : on ne peut pas rester appuyer dessus comme la barre d'espace par exemple.

Le dépôt de blocs sur la case courante avec déplacement du personnage provoque quelquefois un comportement étrange. Il faut sans doute l'améliorer.

