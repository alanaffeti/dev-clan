<?php

namespace App\Entity;

use App\Repository\ReponseRepository;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;

#[ORM\Entity(repositoryClass: ReponseRepository::class)]
class Reponse
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id = null;

   /*  #[ORM\OneToOne(cascade: ['persist', 'remove'])]
    #[ORM\JoinColumn(nullable: false)]
    private ?Reclamation $reclamation = null; */

    #[ORM\Column(length: 255)]
    #[Assert\NotBlank(message:"Nom is required")]
    private ?string $nomAg = null;

    #[ORM\Column(length: 255)]
    #[Assert\NotBlank(message:"Prenom is required")]
    private ?string $prenomAg = null;

   
    #[ORM\Column(length: 255)]
    private ?string $traitement = null;

    #[ORM\ManyToOne(inversedBy: 'reponses',targetEntity:Reclamation::class
    )]
    private ?Reclamation $reponses = null;

    public function getId(): ?int
    {
        return $this->id;
    }

  /*  public function getReclamation(): ?Reclamation
    {
        return $this->reclamation;
    }

    public function setReclamation(Reclamation $reclamation): self
    {
        $this->reclamation = $reclamation;

        return $this;
    } */

    public function getNomAg(): ?string
    {
        return $this->nomAg;
    }

    public function setNomAg(string $nomAg): self
    {
        $this->nomAg = $nomAg;

        return $this;
    }

    public function getPrenomAg(): ?string
    {
        return $this->prenomAg;
    }

    public function setPrenomAg(string $prenomAg): self
    {
        $this->prenomAg = $prenomAg;

        return $this;
    }

    public function getTraitement(): ?string
    {
        return $this->traitement;
    }

    public function setTraitement(string $traitement): self
    {
        $this->traitement = $traitement;

        return $this;
    }

    public function __toString()
    {
        return (string)$this->getId();
    }

    public function getReponses(): ?Reclamation
    {
        return $this->reponses;
    }

    public function setReponses(?Reclamation $reponses): self
    {
        $this->reponses = $reponses;

        return $this;
    }
}