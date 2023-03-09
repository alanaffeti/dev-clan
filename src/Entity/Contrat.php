<?php

namespace App\Entity;

use App\Repository\ContratRepository;
use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;
use Symfony\Component\Validator\Context\ExecutionContextInterface;
use Symfony\Component\Validator\Constraints\Callback;
use Symfony\Component\Validator\Constraints\NotBlank;


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
    #[Assert\NotBlank(message:"Le champ Dtae de debut ne doit pas être vide.")]

    private ?\DateTimeInterface $datedebut = null;

    #[ORM\Column(type: Types::DATE_MUTABLE)]
    #[Groups("contrats")]
    #[Assert\NotBlank(message:"Le champ date de fin  ne doit pas être vide.")]

    private ?\DateTimeInterface $datefin = null;

    #[ORM\Column]
    #[Groups("contrats")]
    
    private ?float $prix = null;

    #[ORM\Column(length: 255)]
    #[Groups("contrats")]

    private ?string $type = null;

    #[ORM\Column(length: 255)]
    #[Groups("contrats")]
    #[Assert\NotBlank(message:"Invalid Name.")]
    #[Assert\Regex(
        pattern:'/^[a-zA-Z]+$/',
       message:"Invalid Name."
   )]
    private ?string $nomconducteur = null;

    #[ORM\Column(length: 255)]
    #[Groups("contrats")]
    #[Assert\NotBlank(message:"Invalid Name.")]
     #[Assert\Regex(
         pattern:'/^[a-zA-Z]+$/',
        message:"Invalid Name."
    )]

    private ?string $prenomconducteur = null;

    #[ORM\Column]
    
    #[Groups("contrats")]
    #[Assert\NotBlank(message:"Le champ CIN ne doit pas être vide.")]
     #[Assert\Regex(
         pattern:"/^\d{8}$/",
        message:"Le CIN doit contenir exactement 8 chiffres."
    )]
    private ?int $cin = null;

    #[ORM\Column(length: 255)]
    #[Groups("contrats")]
    #[Assert\NotBlank(message:"Le champ region ne doit pas être vide.")]

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

    
    #[Assert\Callback]

    public function validate(ExecutionContextInterface $context, $payload)
    {
        if ($this->datefin <= $this->datedebut) {
            $context->buildViolation('La date de fin doit être supérieure à la date de début.')
                ->atPath('datefin')
                ->addViolation();
        }
    }

}
