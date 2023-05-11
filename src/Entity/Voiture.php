<?php

namespace App\Entity;

use App\Repository\VoitureRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\DBAL\Types\Types;
use Symfony\Component\Validator\Constraints\NotBlank;

use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Serializer\Annotation\Groups;


#[ORM\Entity(repositoryClass: VoitureRepository::class)]
class Voiture
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    #[Groups("voitures")]
    #[ApiProperty]
    private ?int $id = null;

    #[ORM\Column(length: 255)]
    #[Groups("voitures")]

    #[ApiProperty]
    #[Assert\NotBlank(message:"Le champ marque ne doit pas être vide.")]

    private ?string $marque = null;

    #[ORM\Column(length: 255)]
    #[Groups("voitures")]
    #[ApiProperty]
    private ?string $typevoiture = null;

    #[ORM\Column(length: 255)]
    #[Groups("voitures")]
    #[ApiProperty]

    private ?string $matricule = null;
    #[Assert\NotBlank(message:"Le champ matricule ne doit pas être vide.")]

    #[ORM\Column(length: 255)]
    #[Groups("voitures")]
    #[ApiProperty]

    private ?string $modele = null;
    #[Assert\NotBlank(message:"Le champ modele ne doit pas être vide.")]

    #[ORM\Column(type: Types::DATE_MUTABLE)]
    #[Groups("voitures")]
    #[Assert\NotBlank(message:"Le champ date de fabrication ne doit pas être vide.")]

    private ?\DateTimeInterface $datefabrication = null;

    #[ORM\Column(length: 255)]
    #[Groups("voitures")]

    private ?string $typecarburant = null;

    #[ORM\Column]
    #[Groups("voitures")]
    #[Assert\NotBlank(message:"Le champ kilometrage ne doit pas être vide.")]

    private ?int $kilometrage = null;

    #[ORM\Column(length: 255)]
    #[Groups("voitures")]
    #[Assert\NotBlank(message:"Le champ Etat ne doit pas être vide.")]

    private ?string $etat = null;

    #[ORM\OneToMany(mappedBy: 'voiture', targetEntity: Contrat::class, cascade: ["remove"])]
    
    private Collection $contrats;

    public function __construct()
    {
        $this->contrats = new ArrayCollection();
    }

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getMarque(): ?string
    {
        return $this->marque;
    }

    public function setMarque(string $marque): self
    {
        $this->marque = $marque;

        return $this;
    }

    public function getTypevoiture(): ?string
    {
        return $this->typevoiture;
    }

    public function setTypevoiture(string $typevoiture): self
    {
        $this->typevoiture = $typevoiture;

        return $this;
    }

    public function getMatricule(): ?string
    {
        return $this->matricule;
    }

    public function setMatricule(string $matricule): self
    {
        $this->matricule = $matricule;

        return $this;
    }

    public function getModele(): ?string
    {
        return $this->modele;
    }

    public function setModele(string $modele): self
    {
        $this->modele = $modele;

        return $this;
    }

    public function getDatefabrication(): ?\DateTimeInterface
    {
        return $this->datefabrication;
    }

    public function setDatefabrication(\DateTimeInterface $datefabrication): self
    {
        $this->datefabrication = $datefabrication;

        return $this;
    }

    public function getTypecarburant(): ?string
    {
        return $this->typecarburant;
    }

    public function setTypecarburant(string $typecarburant): self
    {
        $this->typecarburant = $typecarburant;

        return $this;
    }

    public function getKilometrage(): ?int
    {
        return $this->kilometrage;
    }

    public function setKilometrage(int $kilometrage): self
    {
        $this->kilometrage = $kilometrage;

        return $this;
    }

    public function getEtat(): ?string
    {
        return $this->etat;
    }

    public function setEtat(string $etat): self
    {
        $this->etat = $etat;

        return $this;
    }

    /**
     * @return Collection<int, Contrat>
     */
    public function getContrats(): Collection
    {
        return $this->contrats;
    }

    public function addContrat(Contrat $contrat): self
    {
        if (!$this->contrats->contains($contrat)) {
            $this->contrats->add($contrat);
            $contrat->setVoiture($this);
        }

        return $this;
    }

    public function removeContrat(Contrat $contrat): self
    {
        if ($this->contrats->removeElement($contrat)) {
            // set the owning side to null (unless already changed)
            if ($contrat->getVoiture() === $this) {
                $contrat->setVoiture(null);
            }
        }

        return $this;
    }
}
