<?php

namespace App\Entity;

use App\Repository\ContratRepository;
use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity(repositoryClass: ContratRepository::class)]
class Contrat
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    #[Groups("contrats")]

    private ?int $id = null;

    #[ORM\Column(type: Types::DATE_MUTABLE)]
    #[Groups("contrats")]

    private ?\DateTimeInterface $datedebut = null;

    #[ORM\Column(type: Types::DATE_MUTABLE)]
    #[Groups("contrats")]

    private ?\DateTimeInterface $datefin = null;

    #[ORM\Column]
    #[Groups("contrats")]

    private ?float $prix = null;

    #[ORM\Column(length: 255)]
    #[Groups("contrats")]

    private ?string $type = null;

    #[ORM\Column(length: 255)]
    #[Groups("contrats")]

    private ?string $nomconducteur = null;

    #[ORM\Column(length: 255)]
    #[Groups("contrats")]

    private ?string $prenomconducteur = null;

    #[ORM\Column]
    #[Groups("contrats")]

    private ?int $cin = null;

    #[ORM\Column(length: 255)]
    #[Groups("contrats")]

    private ?string $region = null;

    #[ORM\ManyToOne(inversedBy: 'contrats')]
    #[ORM\JoinColumn(nullable: false)]
    private ?Voiture $voiture = null;

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getDatedebut(): ?\DateTimeInterface
    {
        return $this->datedebut;
    }

    public function setDatedebut(\DateTimeInterface $datedebut): self
    {
        $this->datedebut = $datedebut;

        return $this;
    }

    public function getDatefin(): ?\DateTimeInterface
    {
        return $this->datefin;
    }

    public function setDatefin(\DateTimeInterface $datefin): self
    {
        $this->datefin = $datefin;

        return $this;
    }

    public function getPrix(): ?float
    {
        return $this->prix;
    }

    public function setPrix(float $prix): self
    {
        $this->prix = $prix;

        return $this;
    }

    public function getType(): ?string
    {
        return $this->type;
    }

    public function setType(string $type): self
    {
        $this->type = $type;

        return $this;
    }

    public function getNomconducteur(): ?string
    {
        return $this->nomconducteur;
    }

    public function setNomconducteur(string $nomconducteur): self
    {
        $this->nomconducteur = $nomconducteur;

        return $this;
    }

    public function getPrenomconducteur(): ?string
    {
        return $this->prenomconducteur;
    }

    public function setPrenomconducteur(string $prenomconducteur): self
    {
        $this->prenomconducteur = $prenomconducteur;

        return $this;
    }

    public function getCin(): ?int
    {
        return $this->cin;
    }

    public function setCin(int $cin): self
    {
        $this->cin = $cin;

        return $this;
    }

    public function getRegion(): ?string
    {
        return $this->region;
    }

    public function setRegion(string $region): self
    {
        $this->region = $region;

        return $this;
    }

    public function getVoiture(): ?Voiture
    {
        return $this->voiture;
    }

    public function setVoiture(?Voiture $voiture): self
    {
        $this->voiture = $voiture;

        return $this;
    }
}
