<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

/**
 * Auto-generated Migration: Please modify to your needs!
 */
final class Version20230510101842 extends AbstractMigration
{
    public function getDescription(): string
    {
        return '';
    }

    public function up(Schema $schema): void
    {
        // this up() migration is auto-generated, please modify it to your needs
        $this->addSql('CREATE TABLE accident (id INT AUTO_INCREMENT NOT NULL, lieu_mecaniciens_id INT NOT NULL, nom_client VARCHAR(255) NOT NULL, prenom_client VARCHAR(255) NOT NULL, matricule VARCHAR(255) NOT NULL, image VARCHAR(255) DEFAULT NULL, image_voiture_reparee VARCHAR(255) DEFAULT NULL, image_piece_endommage VARCHAR(255) DEFAULT NULL, image_nouveau_pieces VARCHAR(255) DEFAULT NULL, image_facture_reparation VARCHAR(255) DEFAULT NULL, INDEX IDX_8F31DB6FB198E953 (lieu_mecaniciens_id), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE clientt (id INT AUTO_INCREMENT NOT NULL, name VARCHAR(180) NOT NULL, lastname VARCHAR(180) NOT NULL, email VARCHAR(180) NOT NULL, roles LONGTEXT NOT NULL COMMENT \'(DC2Type:json)\', password VARCHAR(255) NOT NULL, reset_token VARCHAR(255) NOT NULL, num_tel VARCHAR(255) NOT NULL, cin VARCHAR(255) NOT NULL, genre VARCHAR(255) NOT NULL, UNIQUE INDEX UNIQ_9EACBBD7E7927C74 (email), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE constat (id INT AUTO_INCREMENT NOT NULL, expert_id INT DEFAULT NULL, id_client INT NOT NULL, id_vehicule INT NOT NULL, date DATE NOT NULL, lieu VARCHAR(255) NOT NULL, image VARCHAR(255) DEFAULT NULL, INDEX IDX_F96EDD98C5568CE4 (expert_id), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE contrat (id INT AUTO_INCREMENT NOT NULL, voiture_id INT NOT NULL, datedebut DATE NOT NULL, datefin DATE NOT NULL, prix DOUBLE PRECISION NOT NULL, type VARCHAR(255) NOT NULL, nomconducteur VARCHAR(255) NOT NULL, prenomconducteur VARCHAR(255) NOT NULL, cin INT NOT NULL, region VARCHAR(255) NOT NULL, INDEX IDX_60349993181A8BA (voiture_id), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE expert (id INT AUTO_INCREMENT NOT NULL, nom VARCHAR(255) NOT NULL, prenom VARCHAR(255) NOT NULL, cin INT NOT NULL, PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE facture (id INT AUTO_INCREMENT NOT NULL, quantite INT NOT NULL, nom_piece VARCHAR(255) NOT NULL, nom_client VARCHAR(255) NOT NULL, id_client INT NOT NULL, montant_facture DOUBLE PRECISION NOT NULL, PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE facturemeca (id_fact INT AUTO_INCREMENT NOT NULL, article VARCHAR(255) DEFAULT NULL, quantite VARCHAR(255) DEFAULT NULL, prixsanstva VARCHAR(255) DEFAULT NULL, prixavectva VARCHAR(255) DEFAULT NULL, total VARCHAR(255) DEFAULT NULL, description VARCHAR(255) DEFAULT NULL, PRIMARY KEY(id_fact)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE project (id INT AUTO_INCREMENT NOT NULL, created_at DATETIME DEFAULT NULL, updated_at DATETIME DEFAULT NULL, nom VARCHAR(255) NOT NULL, prenom VARCHAR(255) NOT NULL, cin VARCHAR(255) NOT NULL, adresse_mail VARCHAR(255) NOT NULL, num_tel VARCHAR(255) NOT NULL, salaire DOUBLE PRECISION NOT NULL, disponiblite TINYINT(1) NOT NULL, lieu_garage VARCHAR(255) NOT NULL, facture_rep VARCHAR(255) DEFAULT NULL, user_name VARCHAR(255) NOT NULL, password VARCHAR(255) NOT NULL, PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE reclamation (id INT AUTO_INCREMENT NOT NULL, nom VARCHAR(255) NOT NULL, prenom VARCHAR(255) NOT NULL, email VARCHAR(255) NOT NULL, description LONGTEXT NOT NULL, date_rec DATE DEFAULT NULL, type_rec INT NOT NULL, PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE remboursement (id INT AUTO_INCREMENT NOT NULL, date_remb DATE NOT NULL, montant_remb DOUBLE PRECISION NOT NULL, PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE reponse (id INT AUTO_INCREMENT NOT NULL, reponses_id INT DEFAULT NULL, nom_ag VARCHAR(255) NOT NULL, prenom_ag VARCHAR(255) NOT NULL, traitement VARCHAR(255) NOT NULL, INDEX IDX_5FB6DEC7E4084792 (reponses_id), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE user (id INT AUTO_INCREMENT NOT NULL, name VARCHAR(180) NOT NULL, lastname VARCHAR(180) NOT NULL, email VARCHAR(180) NOT NULL, roles LONGTEXT NOT NULL COMMENT \'(DC2Type:json)\', password VARCHAR(255) NOT NULL, reset_token VARCHAR(255) NOT NULL, UNIQUE INDEX UNIQ_8D93D649E7927C74 (email), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE voiture (id INT AUTO_INCREMENT NOT NULL, marque VARCHAR(255) NOT NULL, typevoiture VARCHAR(255) NOT NULL, matricule VARCHAR(255) NOT NULL, modele VARCHAR(255) NOT NULL, datefabrication DATE NOT NULL, typecarburant VARCHAR(255) NOT NULL, kilometrage INT NOT NULL, etat VARCHAR(255) NOT NULL, PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE messenger_messages (id BIGINT AUTO_INCREMENT NOT NULL, body LONGTEXT NOT NULL, headers LONGTEXT NOT NULL, queue_name VARCHAR(190) NOT NULL, created_at DATETIME NOT NULL, available_at DATETIME NOT NULL, delivered_at DATETIME DEFAULT NULL, INDEX IDX_75EA56E0FB7336F0 (queue_name), INDEX IDX_75EA56E0E3BD61CE (available_at), INDEX IDX_75EA56E016BA31DB (delivered_at), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('ALTER TABLE accident ADD CONSTRAINT FK_8F31DB6FB198E953 FOREIGN KEY (lieu_mecaniciens_id) REFERENCES project (id)');
        $this->addSql('ALTER TABLE constat ADD CONSTRAINT FK_F96EDD98C5568CE4 FOREIGN KEY (expert_id) REFERENCES expert (id)');
        $this->addSql('ALTER TABLE contrat ADD CONSTRAINT FK_60349993181A8BA FOREIGN KEY (voiture_id) REFERENCES voiture (id)');
        $this->addSql('ALTER TABLE reponse ADD CONSTRAINT FK_5FB6DEC7E4084792 FOREIGN KEY (reponses_id) REFERENCES reclamation (id)');
    }

    public function down(Schema $schema): void
    {
        // this down() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE accident DROP FOREIGN KEY FK_8F31DB6FB198E953');
        $this->addSql('ALTER TABLE constat DROP FOREIGN KEY FK_F96EDD98C5568CE4');
        $this->addSql('ALTER TABLE contrat DROP FOREIGN KEY FK_60349993181A8BA');
        $this->addSql('ALTER TABLE reponse DROP FOREIGN KEY FK_5FB6DEC7E4084792');
        $this->addSql('DROP TABLE accident');
        $this->addSql('DROP TABLE clientt');
        $this->addSql('DROP TABLE constat');
        $this->addSql('DROP TABLE contrat');
        $this->addSql('DROP TABLE expert');
        $this->addSql('DROP TABLE facture');
        $this->addSql('DROP TABLE facturemeca');
        $this->addSql('DROP TABLE project');
        $this->addSql('DROP TABLE reclamation');
        $this->addSql('DROP TABLE remboursement');
        $this->addSql('DROP TABLE reponse');
        $this->addSql('DROP TABLE user');
        $this->addSql('DROP TABLE voiture');
        $this->addSql('DROP TABLE messenger_messages');
    }
}
