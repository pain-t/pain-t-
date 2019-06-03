# Rapport de projet Java : <br/>Application de dessin vectoriel (ébauche)

# Pain-t
<!-- ![pain-t](./resources/pain-t.jpg | width=100) -->
<img width="200" alt="pain-t" src="resources/pain-t.jpg">


## Fonctionnalités du prototype
L'application a été développée selon le cahier des charges et les fonctionnalités bonus ont été reprises de l'application Microsoft Paint.

Voici la liste exhaustive des fonctionnalités :
* Sélection :
   * Sélection de la forme au click ;
   * Sélection de plusieurs formes en appuyant sur **Shift** tout en cliquant sur les formes ;
   * Sélection des formes au lasso (click and drag) ;
   * Sélection de toutes les formes via **Ctrl + A** ;
   * Navigation et sélection des formes via **Tab** ;
   * Désélection de toutes les formes sélectionnées via **Echap** ;
* Déplacement :
   *  Déplacement des formes sélectionnées au click and drag ;
   *  Déplacement des formes sélectionnées via les flèches du clavier ;
* Création des formes à la volée ;
* Modification :
   * Modification des attributs des formes à la volée ;
   * Redimensionnement des formes via les *handlers* ;
   * Pivot de 90° des formes sélectionnées ;
   * Changement de plan d'un figure ;
* Sauvegarde :
   * Sauvegarde sous le format d'une image ;
   * Sauvegarde sous l'image d'un script (afin de reprendre l'édition du projet) ;
   * Chargement du script ;
* Outils :
   * Zoom du canevas (pas de modification de la taille des formes)


### Raccourcis clavier :
   * **Ctrl + A** : Sélection de toutes les formes ;
   * **Tab** : Navigation et sélection des formes ;
   * **Echap** : Désélection de toutes les formes sélectionnées ;
   * **Suppr** : Suppression des formes sélectionnées ;
   * **Ctrl + C** : Copie des formes sélectionnées ;
   * **Ctrl + V** : Collage des formes copiées ;
   * **Ctrl + X** : Copie et collage des formes sélectionnées ;
   * **Flèches du clavier** : Déplacement les formes sélectionnées ;
   * **P** : Pivot de 90° des formes sélectionnées ;
   * **1** : Met la dernière forme sélectionnée à un plan plus proche ;
   * **2** : Met la dernière forme sélectionnée à un plan plus éloigné ;


## Choix de l'implantation

Dans le projet initial, le modèle ne notifiait pas la vue d'un éventuel changement. La première modification a donc été de rajouter cette fonctionnalité. Pour se faire, la classe [**ShapeModel**](./src/graphics/shapes/ShapeModel.java) a été ajoutée. Elle contient notamment la création des différentes formes de base et la récupération de ces formes.

![](./resources/Observer.png)

La classe **Shape** et la classe **Attributes** implémentent toutes les deux l'interface **Observable**, celle-ci permettant d'ajouter des observateurs.
Les méthodes de modification de ces classes appellent la méthode **notifyObservers()**, qui appelle à son tour la méthode **notify()** de l'observer, qui raffraichit la fenêtre pour mettre à jour la vue.

**Nico, tu écriras ton bordel.<br/>**
Afin de supporter les nouvelles fonctionnalités, l'interface a subit de nombreux changements (développement par *Nicolas Herr*).
Pour pouvoir modifier plus facilement les shapes, un bandeau de contrôle a été ajouté.

![](./resources/bandeau.png)

Il est décomposé en trois **JPanel**.
* Le panel pour la création de formes de base.
* Le panel pour la modification des couleurs des formes.
* Le panel de modification des attributs du texte.

Dans le premier **JPanel**, les formes sont créées. [Voir Partie leslie](#leslie)

Dans le second **JPanel**, nous avons tous les modificateurs liés à la couleurs. Lors d'un clic sur une formes, les éléments s'adaptent en fonction de la forme selectionnée. Si la couleur de remplissage est activée, la **JCheckBox** est cochée.
Le bouton de remplissage et le bouton de contour, sont des **JButton** customisés.
La méthode [**paintComponent()**](./src/shapes/ui/component/ButtonColor.java#L58) a été réécrite. Le petit rectangle gris sur l'image indique la couleur en cours.

![](./resources/popup.png)

Lors du clic sur un bouton, un menu **JPopupMenu** s'ouvre. Cette popup contient un élément permettant de choisir la couleur voulue ainsi qu'un bouton de validation et un d'abandon.  
Le sélectionneur de couleur est un **JColorChooser** customisé [voir **ColorChooser**](./src/graphics/shapes/ui/component/ColorChooser.java).

* Un curseur en forme de croix permet de choisir la couleur. Différentes couleurs sont disponibles en cliquant sur la colonne de couleur sur la droite.  
* Le bouton **OK** permet d'appliquer la couleur à la forme sélectionnée.
* Le bouton **ABORT** permet de fermer la popup sans rien modifier.


La réalisation de ce sélecteur de couleur a posé quelques problèmes. En effet, le composant JColorChooser est un sélecteur générique avec beaucoup trop de fonctionnalités pour les actions que nous voulions. J'ai donc voulu dans un premier temps créer notre propre sélecteur de couleur. Après avoir passé du temps à essayer de créer ce selecteur de couleur sans succès, j'ai décidé de prendre uniquement le rectangle de couleur. Le problème est que la classe est protégée. Il est donc impossible de l'instancier depuis notre projet. J'ai donc dû créer un JColorChooser et enlever les parties qui ne m'intéressaient pas : les différents onglets et le panel de prévisualisation.
Dans l'onglet restant, il restait les barres contenant la valeur des composantes RGB. Étant assez encombrantes, je les ai également enlevées. Après avoir récupéré ce composant, je l'ai ajouté dans un JPopupMenu. J'ai choisi de le mettre dans ce composant : je ne souhaitais pas le mettre dans une nouvelle JFrame. Le JPopupMenu est plus compact et impacte moins sur la visualisation des formes dans l'interface. Dans le JPopupMenu se trouve un panel contenant un GridBagLayout. Ce qui me permet de placer les éléments facilement.

Dans le dernier, il y a tous les modificateurs liés à la police de texte. Comme le JPanel de choix de couleurs, les attributs se mettent à jour automatiquement lors d'un clic sur une forme de type texte. Nous avons la possibilité de modifier trois paramètres de la police.

* La police : toutes les polices disponibles de l'ordinateur y sont présentes.
* La taille de la police : de 10 a 38px.
* La couleur de la police.

La police et la taille sont dans deux listes déroulantes. Une fois un texte selectionné, on peut changer ses attributs. Le changement se fait lors de la selection d'un nouvel élément de la liste à l'aide d'un **ActionListener**.

**Manon, tu écriras ton bordel.<br/>**
Les formes retenues sont le **SRectangle**, le **SOval**, le **SText** et la **SCollection**. Le SCircle a été remplacé par le SOval, puisque plus logique au niveau du programme. L'objet **Graphics2D** au niveau du **ShapesDraftman** dessine les formes selon leurs limites, selon le cadre les contenant. Ainsi, en implantant le SOval comme une classe fille de SRectangle, SOval peut hériter de ses comportements (comme le redimensionnement via les handlers).

**JN, tu écriras ton bordel.<br/>**

### <a name="leslie"></a>

**Leslie, tu écriras ton bordel.<br/>**

## Créateurs
* Nicolas Herr ;
* Manon Heyser ;
* Jean-Noël Balanche ;
* Leslie Caron.
