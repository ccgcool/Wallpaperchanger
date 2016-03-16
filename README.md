# Wallpaperchanger
An android app that lets You use images from other apps as wallpaper and cycles through them on every screen lock

It statically registers itself to a bootcomplete system broadcast.
Uponbootcomplete system broadcast reception, It dynamically registers for a screen locked broadcast.
Upon screenlock broadcast received, it sets a new stored wallpaper whose index is controlled using SharedPreferences

The app sets an intent filter for its activity to receive SEND Action intents of mimetype image.
The image info is extracted from received intent and used to store a copy in external storage.
Files are named as order of index.

Planned features:
1) Use sqlitedb to manage image names and storage and app updates.
2) Create activity to allow user to deselect any previously hsared images.
