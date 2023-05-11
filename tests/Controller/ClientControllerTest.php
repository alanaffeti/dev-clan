<?php

namespace App\Test\Controller;

use App\Entity\Client;
use App\Repository\ClientRepository;
use Symfony\Bundle\FrameworkBundle\KernelBrowser;
use Symfony\Bundle\FrameworkBundle\Test\WebTestCase;

class ClientControllerTest extends WebTestCase
{
    private KernelBrowser $client;
    private ClientRepository $repository;
    private string $path = '/client/';

    protected function setUp(): void
    {
        $this->client = static::createClient();
        $this->repository = static::getContainer()->get('doctrine')->getRepository(Client::class);

        foreach ($this->repository->findAll() as $object) {
            $this->repository->remove($object, true);
        }
    }

    public function testIndex(): void
    {
        $crawler = $this->client->request('GET', $this->path);

        self::assertResponseStatusCodeSame(200);
        self::assertPageTitleContains('Client index');

        // Use the $crawler to perform additional assertions e.g.
        // self::assertSame('Some text on the page', $crawler->filter('.p')->first());
    }

    public function testNew(): void
    {
        $originalNumObjectsInRepository = count($this->repository->findAll());

        $this->markTestIncomplete();
        $this->client->request('GET', sprintf('%snew', $this->path));

        self::assertResponseStatusCodeSame(200);

        $this->client->submitForm('Save', [
            'client[name]' => 'Testing',
            'client[lastname]' => 'Testing',
            'client[email]' => 'Testing',
            'client[roles]' => 'Testing',
            'client[password]' => 'Testing',
            'client[adresse]' => 'Testing',
            'client[numTel]' => 'Testing',
            'client[DateDeNaissance]' => 'Testing',
            'client[Genre]' => 'Testing',
        ]);

        self::assertResponseRedirects('/client/');

        self::assertSame($originalNumObjectsInRepository + 1, count($this->repository->findAll()));
    }

    public function testShow(): void
    {
        $this->markTestIncomplete();
        $fixture = new Client();
        $fixture->setName('My Title');
        $fixture->setLastname('My Title');
        $fixture->setEmail('My Title');
        $fixture->setRoles('My Title');
        $fixture->setPassword('My Title');
        $fixture->setAdresse('My Title');
        $fixture->setNumTel('My Title');
        $fixture->setDateDeNaissance('My Title');
        $fixture->setGenre('My Title');

        $this->repository->save($fixture, true);

        $this->client->request('GET', sprintf('%s%s', $this->path, $fixture->getId()));

        self::assertResponseStatusCodeSame(200);
        self::assertPageTitleContains('Client');

        // Use assertions to check that the properties are properly displayed.
    }

    public function testEdit(): void
    {
        $this->markTestIncomplete();
        $fixture = new Client();
        $fixture->setName('My Title');
        $fixture->setLastname('My Title');
        $fixture->setEmail('My Title');
        $fixture->setRoles('My Title');
        $fixture->setPassword('My Title');
        $fixture->setAdresse('My Title');
        $fixture->setNumTel('My Title');
        $fixture->setDateDeNaissance('My Title');
        $fixture->setGenre('My Title');

        $this->repository->save($fixture, true);

        $this->client->request('GET', sprintf('%s%s/edit', $this->path, $fixture->getId()));

        $this->client->submitForm('Update', [
            'client[name]' => 'Something New',
            'client[lastname]' => 'Something New',
            'client[email]' => 'Something New',
            'client[roles]' => 'Something New',
            'client[password]' => 'Something New',
            'client[adresse]' => 'Something New',
            'client[numTel]' => 'Something New',
            'client[DateDeNaissance]' => 'Something New',
            'client[Genre]' => 'Something New',
        ]);

        self::assertResponseRedirects('/client/');

        $fixture = $this->repository->findAll();

        self::assertSame('Something New', $fixture[0]->getName());
        self::assertSame('Something New', $fixture[0]->getLastname());
        self::assertSame('Something New', $fixture[0]->getEmail());
        self::assertSame('Something New', $fixture[0]->getRoles());
        self::assertSame('Something New', $fixture[0]->getPassword());
        self::assertSame('Something New', $fixture[0]->getAdresse());
        self::assertSame('Something New', $fixture[0]->getNumTel());
        self::assertSame('Something New', $fixture[0]->getDateDeNaissance());
        self::assertSame('Something New', $fixture[0]->getGenre());
    }

    public function testRemove(): void
    {
        $this->markTestIncomplete();

        $originalNumObjectsInRepository = count($this->repository->findAll());

        $fixture = new Client();
        $fixture->setName('My Title');
        $fixture->setLastname('My Title');
        $fixture->setEmail('My Title');
        $fixture->setRoles('My Title');
        $fixture->setPassword('My Title');
        $fixture->setAdresse('My Title');
        $fixture->setNumTel('My Title');
        $fixture->setDateDeNaissance('My Title');
        $fixture->setGenre('My Title');

        $this->repository->save($fixture, true);

        self::assertSame($originalNumObjectsInRepository + 1, count($this->repository->findAll()));

        $this->client->request('GET', sprintf('%s%s', $this->path, $fixture->getId()));
        $this->client->submitForm('Delete');

        self::assertSame($originalNumObjectsInRepository, count($this->repository->findAll()));
        self::assertResponseRedirects('/client/');
    }
}
