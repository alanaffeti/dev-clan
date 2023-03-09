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

    

    #[ORM\Column(type:"integer") ]
    #[Assert\Range(min:1, minMessage:"L'ID doit être supérieur à {{ limit }}.")]
    #[Groups("constats")]
    private  $id_client ;

    #[ORM\Column(type:"integer")]
    #[Groups("constats")]
    private  $id_vehicule ;

    #[ORM\Column(type: Types::DATE_MUTABLE)]
    #[Groups("constats")]
    private ?\DateTimeInterface $date = null;

    #[ORM\Column(length: 255)]
    #[Groups("constats")]
    private ?string $lieu = null;
   
    #[ORM\Column(type:"string", length:255, nullable:true)]
    #[Groups("constats")]
    private $image;

    #[ORM\ManyToOne(inversedBy: 'constats')]
    private ?Expert $expert = null;
    

    public function getImage(){
        return $this->image;
    }

    public function setImage($image){


    $this->image = $image;
    return $this;

    }
    
    
    public function getId(): ?int
    {
        return $this->id;
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

    public function getIdvehicule(): ?int
    {
        return $this->id_vehicule;
    }

    public function setIdvehicule(int $id_vehicule): self
    {
        $this->id_vehicule = $id_vehicule;

        return $this;
    }

    public function getDate(): ?\DateTimeInterface
    {
        return $this->date;
    }

    public function setDate(\DateTimeInterface $date): self
    {
        $this->date = $date;

        return $this;
    }

    public function getLieu(): ?string
    {
        return $this->lieu;
    }

    public function setLieu(string $lieu): self
    {
        $this->lieu = $lieu;

        return $this;
    }

    public function getExpert(): ?Expert
    {
        return $this->expert;
    }

    public function setExpert(?Expert $expert): self
    {
        $this->expert = $expert;

        return $this;
    }


   


}
