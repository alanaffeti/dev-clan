<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

/**
 * Auto-generated Migration: Please modify to your needs!
 */
final class Version20230221211619 extends AbstractMigration
{
    public function getDescription(): string
    {
        return '';
    }

    public function up(Schema $schema): void
    {
        // this up() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE accident ADD image_voiture_reparee TINYBLOB DEFAULT NULL, ADD image_piece_endommage TINYBLOB DEFAULT NULL, ADD image_nouveau_pieces TINYBLOB DEFAULT NULL, ADD image_facture_reparation TINYBLOB DEFAULT NULL');
    }

    public function down(Schema $schema): void
    {
        // this down() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE accident DROP image_voiture_reparee, DROP image_piece_endommage, DROP image_nouveau_pieces, DROP image_facture_reparation');
    }
}
