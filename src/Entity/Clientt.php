<?php

namespace App\Entity;

use App\Repository\ClienttRepository;
use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;

#[ORM\Entity(repositoryClass: ClienttRepository::class)]
class Clientt extends User
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id = null;

    #[ORM\Column(length: 255)]
    #[Assert\Regex(
            pattern:"/^\d{8}$/",
            message:"Le numéro de téléphone doit contenir exactement 8 chiffres."
         )]
    private ?string $numTel = null;


    
    #[ORM\Column(length: 255)]
    #[Assert\Regex(
           pattern:"/^[a-zA-Z]{1,2}[0-9]{3,6}$/",
           message:"Le CIN doit être une chaîne de 8 caractères, commençant par 1 ou 2 lettres suivies de 3 à 6 chiffres."
        )]
    private ?string $cin = null;

    

    #[ORM\Column(length: 255)]
    private ?string $genre = null;

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getNumTel(): ?string
    {
        return $this->numTel;
    }

    public function setNumTel(string $numTel): self
    {
        $this->numTel = $numTel;

        return $this;
    }

    public function getCin(): ?string
    {
        return $this->cin;
    }

    public function setCin(string $cin): self
    {
        $this->cin = $cin;

        return $this;
    }

    

    public function getGenre(): ?string
    {
        return $this->genre;
    }

    public function setGenre(string $genre): self
    {
        $this->genre = $genre;

        return $this;
    }
}
