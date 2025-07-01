-- Script PostgreSQL pour BD_Location_Voitures
-- Créer la base de données (à exécuter séparément)
-- CREATE DATABASE BD_Location_Voitures;

-- Table Utilisateur
CREATE TABLE IF NOT EXISTS utilisateur (
  id SERIAL PRIMARY KEY,
  nom VARCHAR(100),
  prenom VARCHAR(100),
  email VARCHAR(255) UNIQUE NOT NULL,
  mot_de_passe TEXT NOT NULL,
  date_naissance DATE,
  adresse TEXT,
  role VARCHAR(50),
  date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table Agence
CREATE TABLE IF NOT EXISTS agence (
  id SERIAL PRIMARY KEY,
  nom VARCHAR(100),
  ville VARCHAR(100),
  pays VARCHAR(100),
  adresse TEXT
);

-- Table Véhicule
CREATE TABLE IF NOT EXISTS vehicule (
  id SERIAL PRIMARY KEY,
  marque VARCHAR(100),
  modele VARCHAR(100),
  categorie VARCHAR(10),
  transmission VARCHAR(50),
  climatisation BOOLEAN,
  gps BOOLEAN
);

-- Table Offre de Location
CREATE TABLE IF NOT EXISTS offre_location (
  id SERIAL PRIMARY KEY,
  vehicule_id INTEGER REFERENCES vehicule(id),
  agence_depart_id INTEGER REFERENCES agence(id),
  agence_retour_id INTEGER REFERENCES agence(id),
  date_heure_depart TIMESTAMP,
  date_heure_retour TIMESTAMP,
  tarif NUMERIC(10,2)
);

-- Table Réservation
CREATE TABLE IF NOT EXISTS reservation (
  id SERIAL PRIMARY KEY,
  utilisateur_id INTEGER REFERENCES utilisateur(id),
  offre_location_id INTEGER REFERENCES offre_location(id),
  statut VARCHAR(50),
  date_reservation TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table Paiement
CREATE TABLE IF NOT EXISTS paiement (
  id SERIAL PRIMARY KEY,
  reservation_id INTEGER REFERENCES reservation(id),
  montant NUMERIC(10,2),
  date_paiement TIMESTAMP,
  statut VARCHAR(50),
  methode_paiement VARCHAR(50)
);

-- Table Messages support
CREATE TABLE IF NOT EXISTS message_support (
  id SERIAL PRIMARY KEY,
  utilisateur_id INTEGER REFERENCES utilisateur(id),
  type VARCHAR(50),
  contenu TEXT,
  statut VARCHAR(50),
  date_envoi TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Index pour améliorer les performances
CREATE INDEX IF NOT EXISTS idx_message_support_utilisateur_id ON message_support(utilisateur_id);
CREATE INDEX IF NOT EXISTS idx_message_support_date_envoi ON message_support(date_envoi);
CREATE INDEX IF NOT EXISTS idx_reservation_utilisateur_id ON reservation(utilisateur_id);
CREATE INDEX IF NOT EXISTS idx_offre_location_vehicule_id ON offre_location(vehicule_id); 