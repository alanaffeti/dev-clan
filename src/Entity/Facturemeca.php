<?php

namespace App\Entity;

use App\Repository\FacturemecaRepository;
use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity(repositoryClass: FacturemecaRepository::class)]
class Facturemeca
{


    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id_fact = null;

    #[ORM\Column(length: 255, nullable: true)]
    private ?string $article = null;

    #[ORM\Column(nullable: true)]
    private ?string $quantite = null;

    #[ORM\Column(nullable: true)]
    private ?string $prixsanstva = null;

    #[ORM\Column(nullable: true)]
    private ?string $prixavectva = null;

    #[ORM\Column(nullable: true)]
    private ?string $total = null;

    #[ORM\Column(length: 255, nullable: true)]
    private ?string $description = null;




    public function getIdFact(): ?int
    {
        return $this->id_fact;
    }


    public function setIdFact(?int $id_fact): void
    {
        $this->id_fact = $id_fact;
    }



    public function getArticle(): ?string
    {
        return $this->article;
    }

    public function setArticle(?string $article): self
    {
        $this->article = $article;

        return $this;
    }

    public function getQuantite(): ?string
    {
        return $this->quantite;
    }

    public function setQuantite(?string $quantite): self
    {
        $this->quantite = $quantite;

        return $this;
    }

    public function getPrixsanstva(): ?string
    {
        return $this->prixsanstva;
    }

    public function setPrixsanstva(?string $prixsanstva): self
    {
        $this->prixsanstva = $prixsanstva;

        return $this;
    }

    public function getPrixavectva(): ?string
    {
        return $this->prixavectva;
    }

    public function setPrixavectva(?string $prixavectva): self
    {
        $this->prixavectva = $prixavectva;

        return $this;
    }

    public function getTotal(): ?string
    {
        return $this->total;
    }

    public function setTotal(?string $total): self
    {
        $this->total = $total;

        return $this;
    }

    public function getDescription(): ?string
    {
        return $this->description;
    }

    public function setDescription(?string $description): self
    {
        $this->description = $description;

        return $this;
    }
}
