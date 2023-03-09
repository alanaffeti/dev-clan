<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

/**
 * Auto-generated Migration: Please modify to your needs!
 */
final class Version20230222232625 extends AbstractMigration
{
    public function getDescription(): string
    {
        return '';
    }

    public function up(Schema $schema): void
    {
        // this up() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE facture ADD remboursement_id INT NOT NULL');
        $this->addSql('ALTER TABLE facture ADD CONSTRAINT FK_FE866410F61EE8B FOREIGN KEY (remboursement_id) REFERENCES remboursement (id)');
        $this->addSql('CREATE INDEX IDX_FE866410F61EE8B ON facture (remboursement_id)');
    }

    public function down(Schema $schema): void
    {
        // this down() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE facture DROP FOREIGN KEY FK_FE866410F61EE8B');
        $this->addSql('DROP INDEX IDX_FE866410F61EE8B ON facture');
        $this->addSql('ALTER TABLE facture DROP remboursement_id');
    }
}
