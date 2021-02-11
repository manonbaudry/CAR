# FTP Serveur - CAR 
---------------------------------
Par Baudry Manon & Despelchin Brice

## Fonctionnalitées implémentées ##

- Serveur multithread (multi-connexion autorisée)
- Commandes:
>Get, Dir, Put, User, Pass, Quit, Cd
- connection utilisateur à partir d'un fichier

## Fonctionnalitées non implémentées## 
 - Voir https://fr.wikipedia.org/wiki/Liste_des_commandes_ftp
 - Lock un fichier déjà utilisé par un autre utilisateur (Pas eu le temps de dev)
 Pour ajouter une commande, le processus est le suivant:
	 - Ajouter la commande dans le switch de la classe "FtpServerThread" (méthode  interpreteCommand(String readLine) )
	 - Implémenter la commande dans la classe "FtpServerThread" 
	 -  Ajouter x ou y messages requis dans la classe "Messages" 
