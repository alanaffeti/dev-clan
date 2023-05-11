<?php

namespace App\Entity;

use App\Repository\ProjectRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;
use phpDocumentor\Reflection\Types\Array_;
use Symfony\Component\Validator\Constraints\Length;

#[ORM\Entity(repositoryClass: ProjectRepository::class)]
class Project
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id = null;

    #[ORM\Column(type: Types::DATETIME_MUTABLE,nullable: true)]
    private ?\DateTimeInterface $created_at = null;

    #[ORM\Column(type: Types::DATETIME_MUTABLE, nullable: true)]
    private ?\DateTimeInterface $updated_at = null;

    #[ORM\Column(length: 255)]
    #[Assert\NotBlank(message: "nom ne doit pas etre vide")]
    private ?string $nom = null;

    #[ORM\Column(length: 255)]
    #[Assert\NotBlank(message: "prenom ne doit pas etre vide")]
    private ?string $prenom = null;

    #[ORM\Column(length: 255)]
    #[Assert\NotBlank(message: "cin ne doit pas etre vide")]
    #[Length( min: 8, max: 8, minMessage: "entrer une cin de 8 chiffres",maxMessage: "entrer une cin de 8 chiffres")]
    private ?string $cin = null;

    #[ORM\Column(length: 255)]
    #[Assert\NotBlank(message: "adresse mail ne doit pas etre vide")]
    private ?string $adresse_mail = null;

    #[ORM\Column(length: 255)]
    #[Assert\NotBlank(message: "numero telephone ne doit pas etre vide")]
    #[Length( min: 8, max: 8, minMessage: "entrer une cin de 8 chiffres",maxMessage: "entrer une cin de 8 chiffres")]
    private ?string $num_tel = null;

    #[ORM\Column]
    #[Assert\NotBlank(message: "salaire ne doit pas etre vide")]
    private ?float $salaire = null;

    #[ORM\Column]

    private ?bool $disponiblite = null;

    #[ORM\Column(length: 255)]
    #[Assert\NotBlank(message: "lieu garage ne doit pas etre vide")]
    private ?string $lieu_garage = null;

    #[ORM\Column(type: "string", length: 255, nullable: true)]
    private  $facture_rep = null;

    #[ORM\Column(length: 255)]
    #[Assert\NotBlank(message: "user ne doit pas etre vide")]
    private ?string $user_name = null;

    #[ORM\Column(length: 255)]
    #[Assert\NotBlank(message: "password ne doit pas etre vide")]
    private ?string $password = null;

    #[ORM\OneToMany(mappedBy: 'lieu_mecaniciens', targetEntity: Accident::class, cascade: ['persist'], orphanRemoval: true)]
    private  $accidents;



    public function __construct()
    {
        $this->accidents = new ArrayCollection();

    }



    public function getId(): ?int
    {
        return $this->id;
    }



    public function getCreatedAt(): ?\DateTimeInterface
    {
        return $this->created_at;
    }

    public function setCreatedAt(\DateTimeInterface $created_at): self
    {
        $this->created_at = $created_at;

        return $this;
    }

    public function getUpdatedAt(): ?\DateTimeInterface
    {
        return $this->updated_at;
    }

    public function setUpdatedAt(?\DateTimeInterface $updated_at): self
    {
        $this->updated_at = $updated_at;

        return $this;
    }

    public function getNom(): ?string
    {
        return $this->nom;
    }

    public function setNom(string $nom): self
    {
        $this->nom = $nom;

        return $this;
    }

    public function getPrenom(): ?string
    {
        return $this->prenom;
    }

    public function setPrenom(string $prenom): self
    {
        $this->prenom = $prenom;

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

    public function getAdresseMail(): ?string
    {
        return $this->adresse_mail;
    }

    public function setAdresseMail(string $adresse_mail): self
    {
        $this->adresse_mail = $adresse_mail;

        return $this;
    }

    public function getNumTel(): ?string
    {
        return $this->num_tel;
    }

    public function setNumTel(string $num_tel): self
    {
        $this->num_tel = $num_tel;

        return $this;
    }

    public function getSalaire(): ?float
    {
        return $this->salaire;
    }

    public function setSalaire(float $salaire): self
    {
        $this->salaire = $salaire;

        return $this;
    }

    public function isDisponiblite(): ?bool
    {
        return $this->disponiblite;
    }

    public function setDisponiblite(bool $disponiblite): self
    {
        $this->disponiblite = $disponiblite;

        return $this;
    }

    public function getLieuGarage(): ?string
    {
        return $this->lieu_garage;
    }

    public function setLieuGarage(string $lieu_garage): self
    {
        $this->lieu_garage = $lieu_garage;

        return $this;
    }

    public function getFactureRep()
    {
        return $this->facture_rep;
    }

    public function setFactureRep( $facture_rep): self
    {
        $this->facture_rep = $facture_rep;

        return $this;
    }

    public function getUserName(): ?string
    {
        return $this->user_name;
    }

    public function setUserName(string $user_name): self
    {
        $this->user_name = $user_name;

        return $this;
    }

    public function getPassword(): ?string
    {
        return $this->password;
    }

    public function setPassword(string $password): self
    {
        $this->password = $password;

        return $this;
    }

    /**
     * @return Collection<int, Accident>
     */
    public function getAccidents(): Collection
    {
        return $this->accidents;
    }

    public function addAccident(Accident $accident): self
    {
        if (!$this->accidents->contains($accident)) {
            $this->accidents->add($accident);
            $accident->setVoitures($this);
        }

        return $this;
    }

    public function removeAccident(Accident $accident): self
    {
        if ($this->accidents->removeElement($accident)) {
            // set the owning side to null (unless already changed)
            if ($accident->getVoitures() === $this) {
                $accident->setVoitures(null);
            }
        }

        return $this;
    }





}
