# Trucs et actuces

## Gestion des images

```java
    /**
     * Create a scaled up version of the original icon, to have a MineCraft
     * effect.
     * 
     * @param imageName
     *            the name of the texture file.
     * @return an ImageIcon scaled up to 40x40.
     */
    public static ImageIcon scaled(String imageName) {
        try {
            return new ImageIcon(ImageIO.read(Resources.class.getResource(imageName)).getScaledInstance(DEFAULT_IMAGE_SIZE, DEFAULT_IMAGE_SIZE,
                    Image.SCALE_DEFAULT));
        } catch (IOException e) {
            return new ImageIcon();
        }
    }

    /**
     * Create a new scaled up version of the original icon, over an already
     * scaled image (e.g. STONE).
     * 
     * @param background
     *            a scaled up background image
     * @param imageName
     *            the new image to put on top of the background.
     * @return an image consisting of imageName with the given background.
     */
    public static ImageIcon overlay(ImageIcon background, String imageName) {
        try {
            Image foreground = ImageIO.read(Resources.class.getResource(imageName)).getScaledInstance(DEFAULT_IMAGE_SIZE, DEFAULT_IMAGE_SIZE,
                    Image.SCALE_DEFAULT);
            BufferedImage merged = new BufferedImage(DEFAULT_IMAGE_SIZE, DEFAULT_IMAGE_SIZE, BufferedImage.TYPE_INT_ARGB);
            Graphics g = merged.getGraphics();
            g.drawImage(background.getImage(), 0, 0, null);
            g.drawImage(foreground, 0, 0, null);
            return new ImageIcon(merged);
        } catch (IOException e) {
            return new ImageIcon();
        }
    }
```

## Configuration git sur votre poste

La première fois que vous utilisez git, il faut vous identifier auprès du client git en fournissant une adresse email et un nom :

```
$ git config --global user.email prenom_nom@ens.univ-artois.fr
$ git config --global user.name "Prenom Nom"
```

De plus, comme nous utilisons une connexion https, il faut augmenter la taille des messages HTTP :

```
$ git config --global http.postBuffer 524288000
```

Enfin, pour éviter de taper trop souvent votre mot de passe, vous pouvez indiquer à git de garder
vos identifiants dans un cache (pendant 15 minutes).

```
$ git config --global credential.helper cache
```

## Synchronisation des dépôts

Déclarer le dépot commun à tous les étudiants de DUT2 :

```shell
git remote add upstream https://forge.univ-artois.fr/dut2017/flatcraft.git
```

Mettre à jour son dépôt à partir de dépôt commun :

```shell
git pull upstream master
```
