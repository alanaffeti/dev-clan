<?php

namespace App\Entity;

use App\Repository\RemboursementRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;

#[ORM\Entity(repositoryClass: RemboursementRepository::class)]
class Remboursement
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id = null;
    #[ORM\Column(type: Types::DATE_MUTABLE)]
    private ?\DateTimeInterface $date_remb = null;
    #[ORM\Column]
    private ?float $montant_remb = null;



    public function __construct()
    {
        $this->factures = new ArrayCollection();
    }

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getDateRemb(): ?\DateTimeInterface
    {
        return $this->date_remb;
    }

    public function setDateRemb(\DateTimeInterface $date_remb): self
    {
        $this->date_remb = $date_remb;

        return $this;
    }

    public function getMontantRemb(): ?float
    {
        return $this->montant_remb;
    }

    public function setMontantRemb(float $montant_remb): self
    {
        $this->montant_remb = $montant_remb;

        return $this;
    }

   
   
}
