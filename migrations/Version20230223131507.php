<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

/**
 * Auto-generated Migration: Please modify to your needs!
 */
final class Version20230223131507 extends AbstractMigration
{
    public function getDescription(): string
    {
        return '';
    }

    public function up(Schema $schema): void
    {
        // this up() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE constat DROP FOREIGN KEY FK_F96EDD98C5568CE4');
        $this->addSql('DROP INDEX IDX_F96EDD98C5568CE4 ON constat');
        $this->addSql('ALTER TABLE constat DROP expert_id');
    }

    public function down(Schema $schema): void
    {
        // this down() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE constat ADD expert_id INT NOT NULL');
        $this->addSql('ALTER TABLE constat ADD CONSTRAINT FK_F96EDD98C5568CE4 FOREIGN KEY (expert_id) REFERENCES expert (id)');
        $this->addSql('CREATE INDEX IDX_F96EDD98C5568CE4 ON constat (expert_id)');
    }
}
