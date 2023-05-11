<?php

namespace App\Entity;

use App\Repository\ConstatRepository;
use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;
use Symfony\Component\Serializer\Annotation\Groups;

#[ORM\Entity(repositoryClass: ConstatRepository::class)]
class Constat
{
 #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    #[Groups("constats")]
    private ?int $id = null;

    #[ORM\Column(length: 255)]
    #[Assert\NotBlank(message: "Le champ 'nom' est obligatoire")]
    #[Groups("constats")]
    private ?string $nom = null;

    #[ORM\Column(length: 255)]
    #[Assert\NotBlank(message: "Le champ 'prenom' est obligatoire")]
    #[Groups("constats")]
    private ?string $prenom = null;

    #[ORM\Column(length: 255)]
    #[Assert\NotBlank(message: "Le champ 'matricule' est obligatoire")]
    #[Groups("constats")]
    private ?string $matricule = null;

    #[ORM\Column(type: Types::DATE_MUTABLE)]
    #[Groups("constats")]
    private ?\DateTimeInterface $date = null;

    #[ORM\Column(length: 255)]
    #[Assert\NotBlank(message: "Le champ 'lieu' est obligatoire")]
    #[Groups("constats")]
    private ?string $lieu = null;

    #[ORM\Column(type:"string", length:255, nullable:true)]
    #[Groups("constats")]
    private $image_Degats;

    // #[ORM\ManyToOne(inversedBy: 'constats')]
    // private ?Expert $expert = null;

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getNom(): ?string
    {
        return $this->nom;
    }

    public function setNom(?string $nom): self
    {
        $this->nom = $nom;

        return $this;
    }

    public function getPrenom(): ?string
    {
        return $this->prenom;
    }

    public function setPrenom(?string $prenom): self
    {
        $this->prenom = $prenom;

        return $this;
    }

    public function getMatricule(): ?string
    {
        return $this->matricule;
    }

    public function setMatricule(?string $matricule): self
    {
        $this->matricule = $matricule;

        return $this;
    }

    public function getDate(): ?\DateTimeInterface
    {
        return $this->date;
    }

    public function setDate(?\DateTimeInterface $date): self
    {
        $this->date = $date;

        return $this;
    }

    public function getLieu(): ?string
    {
        return $this->lieu;
    }

    public function setLieu(?string $lieu): self
    {
        $this->lieu = $lieu;

        return $this;
    }

    public function getImageDegats()
    {
        return $this->image_Degats;
    }

    public function setImageDegats($image_Degats)
    {
        $this->image_Degats = $image_Degats;

        return $this;
    }

    // public function getExpert(): ?Expert
    // {
    //     return $this->expert;
    // }

    // public function setExpert(?Expert $expert): self
    // {
    //     $this->expert = $expert;

    //     return $this;
    // }


   


}
