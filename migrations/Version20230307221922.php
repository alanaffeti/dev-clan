<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

/**
 * Auto-generated Migration: Please modify to your needs!
 */
final class Version20230307221922 extends AbstractMigration
{
    public function getDescription(): string
    {
        return '';
    }

    public function up(Schema $schema): void
    {
        // this up() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE clientt DROP blocked, DROP unblocked');
        $this->addSql('ALTER TABLE user DROP blocked, DROP unblocked');
    }

    public function down(Schema $schema): void
    {
        // this down() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE clientt ADD blocked TINYINT(1) NOT NULL, ADD unblocked TINYINT(1) NOT NULL');
        $this->addSql('ALTER TABLE user ADD blocked TINYINT(1) NOT NULL, ADD unblocked TINYINT(1) NOT NULL');
    }
}
