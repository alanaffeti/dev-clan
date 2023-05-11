<?php

namespace App\Entity;

use App\Repository\FactureRepository;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;
use Symfony\Component\Serializer\Annotation\Groups;

#[ORM\Entity(repositoryClass: FactureRepository::class)]
class Facture
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    #[Groups("factures")]
    private ?int $id = null;
    #[Assert\NotBlank(message: "quantité ne doit pas etre vide")]
    #[ORM\Column]
    #[Groups("factures")]
    private ?int $quantite = null;
    #[Assert\NotBlank(message: "nom_piece ne doit pas etre vide")]
    #[ORM\Column(length: 255)]
    #[Groups("factures")]
    private ?string $nom_piece = null;
    #[Assert\NotBlank(message: "nom_client ne doit pas etre vide")]
    #[ORM\Column(length: 255)]
    #[Groups("factures")]
    private ?string $nom_client = null;
    
    #[ORM\Column]
    #[Groups("factures")]
    private ?int $id_client = null;
    #[Assert\NotBlank(message: "ce champ doit etre remplit")]
    #[ORM\Column]
    #[Groups("factures")]
    private ?float $montant_facture = null;

    
    public function getId(): ?int
    {
        return $this->id;
    }

    public function getQuantite(): ?int
    {
        return $this->quantite;
    }

    public function setQuantite(int $quantite): self
    {
        $this->quantite = $quantite;

        return $this;
    }

    public function getNomPiece(): ?string
    {
        return $this->nom_piece;
    }

    public function setNomPiece(string $nom_piece): self
    {
        $this->nom_piece = $nom_piece;

        return $this;
    }

    public function getNomClient(): ?string
    {
        return $this->nom_client;
    }

    public function setNomClient(string $nom_client): self
    {
        $this->nom_client = $nom_client;

        return $this;
    }

    public function getIdClient(): ?int
    {
        return $this->id_client;
    }

    public function setIdClient(int $id_client): self
    {
        $this->id_client = $id_client;

        return $this;
    }

    public function getMontantFacture(): ?float
    {
        return $this->montant_facture;
    }

    public function setMontantFacture(float $montant_facture): self
    {
        $this->montant_facture = $montant_facture;

        return $this;
    }

    
}
