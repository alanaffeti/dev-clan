<?php

namespace App\Entity;

use App\Repository\ReclamationRepository;
use Doctrine\DBAL\Types\Types;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;
use Symfony\Component\Serializer\Annotation\Groups;
use ConsoleTVs\Profanity\Builder;
use ConsoleTVs\Profanity\Facades\Profanity;
/*use Sb\Bundle\StarRatingBundle\Model\RateableInterface;
use Sb\Bundle\StarRatingBundle\Model\RateableTrait;*/



#[ORM\Entity(repositoryClass: ReclamationRepository::class)]
class Reclamation
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    #[Groups("reclamations")]
    private ?int $id = null;

    

    /*#[ORM\Column(length: 255)]
    #[Assert\NotBlank(message: 'Ce champ est obligatoire')]
    */
    #[ORM\Column(length: 255)]
    #[Groups("reclamations")]
    #[Assert\NotBlank(message:"Nom is required")]
    private ?string $nom = null;

    #[ORM\Column(length: 255)]
    #[Groups("reclamations")]
    #[Assert\NotBlank(message:"Prenom is required")]
    private ?string $prenom = null;

    #[ORM\Column(length: 255)]
    #[Groups("reclamations")]
    #[Assert\NotBlank(message:"Email is required")]
    #[Assert\Email(message:"the email '{{ value }}' is not a valid email")]
    private ?string $email = null;

   #[ORM\Column(type: Types::TEXT)]
   #[Groups("reclamations")]
    #[Assert\NotBlank(message:"Description is required")]
    private ?string $description  = null ;
    
    #[ORM\Column(type: Types::DATE_MUTABLE, nullable: true)]
    #[Groups("reclamations")]
   // #[Assert\NotBlank(message: 'Ce champ est obligatoire')]

    public ?\DateTimeInterface $date_rec = null;



    #[ORM\Column(length: 255)]    
    #[Groups("reclamations")]
    #[Assert\NotBlank(message: 'Ce champ est obligatoire')]

    private ?int $type_rec = null;

    /**
     * @ORM\OneToMany(targetEntity="Reponse", mappedBy="reclamation")
     */
    private $reponses;

    public function __construct()
    {
        $this->reponses = new ArrayCollection();
    }

    public function getId(): ?int
    {
        return $this->id;
    }
    public function construct()
    {
        $this->date_rec = new \DateTime();
    }


        

    public function getDescription(): ?string
    {
        return $this->description;
    }

    public function setDescription(?string $description): self
    
    {   
        $clean_words = \ConsoleTVs\Profanity\Builder::blocker($description)->filter();
        $this->description = $clean_words;

        /*$this->description = $description;*/

        return $this;
    }
    
    public function getDateRec(): ?\DateTimeInterface
    {
        return $this->date_rec;
    }

    public function setDateRec(?\DateTimeInterface $date_rec): self
    {
        $this->date_rec = $date_rec;

        return $this;
    }

    public function getTypeRec(): ?int
    {
        return $this->type_rec;
    }

    public function setTypeRec(?int $type_rec): self
    {
        $this->type_rec = $type_rec;

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

    public function getEmail(): ?string
    {
        return $this->email;
    }

    public function setEmail(string $email): self
    {
        $this->email = $email;

        return $this;
    }
    public function __toString()
    {
        return (string)$this->getId();
    }

    /**
     * @return Collection<int, Reponse>
     */
    public function getReponses(): Collection
    {
        return $this->reponses;
    }

    public function addReponse(Reponse $reponse): self
    {
        if (!$this->reponses->contains($reponse)) {
            $this->reponses->add($reponse);
            $reponse->setReponses($this);
        }

        return $this;
    }

    public function removeReponse(Reponse $reponse): self
    {
        if ($this->reponses->removeElement($reponse)) {
            // set the owning side to null (unless already changed)
            if ($reponse->getReponses() === $this) {
                $reponse->setReponses(null);
            }
        }

        return $this;
    }
}

