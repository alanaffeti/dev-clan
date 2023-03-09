<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

/**
 * Auto-generated Migration: Please modify to your needs!
 */
final class Version20230222235243 extends AbstractMigration
{
    public function getDescription(): string
    {
        return '';
    }

    public function up(Schema $schema): void
    {
        // this up() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE accident CHANGE image_voiture_reparee image_voiture_reparee VARCHAR(255) DEFAULT NULL, CHANGE image_piece_endommage image_piece_endommage VARCHAR(255) DEFAULT NULL, CHANGE image_nouveau_pieces image_nouveau_pieces VARCHAR(255) DEFAULT NULL, CHANGE image_facture_reparation image_facture_reparation VARCHAR(255) DEFAULT NULL');
    }

    public function down(Schema $schema): void
    {
        // this down() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE accident CHANGE image_voiture_reparee image_voiture_reparee TINYBLOB DEFAULT NULL, CHANGE image_piece_endommage image_piece_endommage TINYBLOB DEFAULT NULL, CHANGE image_nouveau_pieces image_nouveau_pieces TINYBLOB DEFAULT NULL, CHANGE image_facture_reparation image_facture_reparation TINYBLOB DEFAULT NULL');
    }
}
