<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

/**
 * Auto-generated Migration: Please modify to your needs!
 */
final class Version20230309134148 extends AbstractMigration
{
    public function getDescription(): string
    {
        return '';
    }

    public function up(Schema $schema): void
    {
        // this up() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE accident ADD CONSTRAINT FK_8F31DB6FB198E953 FOREIGN KEY (lieu_mecaniciens_id) REFERENCES project (id)');
        $this->addSql('CREATE INDEX IDX_8F31DB6FB198E953 ON accident (lieu_mecaniciens_id)');
        $this->addSql('ALTER TABLE facturemeca CHANGE quantite quantite VARCHAR(255) DEFAULT NULL, CHANGE description description VARCHAR(255) DEFAULT NULL, CHANGE prixsanstva prixsanstva VARCHAR(255) DEFAULT NULL, CHANGE prixavectva prixavectva VARCHAR(255) DEFAULT NULL, CHANGE total total VARCHAR(255) DEFAULT NULL');
    }

    public function down(Schema $schema): void
    {
        // this down() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE accident DROP FOREIGN KEY FK_8F31DB6FB198E953');
        $this->addSql('DROP INDEX IDX_8F31DB6FB198E953 ON accident');
        $this->addSql('ALTER TABLE facturemeca CHANGE quantite quantite INT DEFAULT NULL, CHANGE prixsanstva prixsanstva DOUBLE PRECISION DEFAULT NULL, CHANGE prixavectva prixavectva DOUBLE PRECISION DEFAULT NULL, CHANGE total total DOUBLE PRECISION DEFAULT NULL, CHANGE description description VARCHAR(255) NOT NULL');
    }
}
