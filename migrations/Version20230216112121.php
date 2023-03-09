<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

/**
 * Auto-generated Migration: Please modify to your needs!
 */
final class Version20230216112121 extends AbstractMigration
{
    public function getDescription(): string
    {
        return '';
    }

    public function up(Schema $schema): void
    {
        // this up() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE project ADD prenom VARCHAR(255) NOT NULL, ADD cin VARCHAR(255) NOT NULL, ADD adresse_mail VARCHAR(255) NOT NULL, ADD num_tel VARCHAR(255) NOT NULL, ADD salaire DOUBLE PRECISION NOT NULL, ADD disponiblite TINYINT(1) NOT NULL, ADD lieu_garage VARCHAR(255) NOT NULL, ADD facture_rep VARCHAR(255) DEFAULT NULL, DROP description, CHANGE name nom VARCHAR(255) NOT NULL');
    }

    public function down(Schema $schema): void
    {
        // this down() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE project ADD name VARCHAR(255) NOT NULL, ADD description LONGTEXT NOT NULL, DROP nom, DROP prenom, DROP cin, DROP adresse_mail, DROP num_tel, DROP salaire, DROP disponiblite, DROP lieu_garage, DROP facture_rep');
    }
}
