<?php

namespace App\Entity;

use App\Repository\AccidentRepository;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\DomCrawler\Image;
use Symfony\Component\Serializer\Annotation\Groups;
use Symfony\Component\Validator\Constraints as Assert;
use Symfony\Component\Validator\Constraints\Length;



#[ORM\Entity(repositoryClass: AccidentRepository::class)]
class Accident
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    #[Groups("accident")]
    private ?int $id = null;

    #[ORM\Column(length: 255)]
    #[Assert\NotBlank(message: "matricule ne doit pas etre vide")]
    #[Groups("accident")]
    private ?string $nom_client = null;

    #[ORM\Column(length: 255)]
    #[Assert\NotBlank(message: "matricule ne doit pas etre vide")]
    #[Groups("accident")]

    private ?string $prenom_client = null;

    #[ORM\Column(length: 255)]
    #[Assert\NotBlank(message: "matricule ne doit pas etre vide")]
    #[Length( min: 5,minMessage: "entrer une matricule au minimum de 5 chiffres")]
    #[Groups("accident")]

    private ?string $matricule = null;

    #[ORM\Column(type: "string", length: 255, nullable: true,)]
    #[Assert\NotBlank(message: "matricule ne doit pas etre vide")]
    #[Groups("accident")]
    private $image;


    #[ORM\Column(type: "string", length: 255, nullable: true,)]
    #[Assert\NotBlank(message: "matricule ne doit pas etre vide")]
    #[Groups("accident")]
    private $image_voiture_reparee;


    #[ORM\Column(type: "string", length: 255, nullable: true)]
    #[Assert\NotBlank(message: "matricule ne doit pas etre vide")]
    #[Groups("accident")]
    private $image_piece_endommage;

    #[ORM\Column(type: "string", length: 255, nullable: true)]
    #[Assert\NotBlank(message: "matricule ne doit pas etre vide")]
    #[Groups("accident")]
    private $image_nouveau_pieces;

    #[ORM\Column(type: "string", length: 255, nullable: true)]
    #[Assert\NotBlank(message: "matricule ne doit pas etre vide")]
    #[Groups("accident")]
    private $image_facture_reparation;

    #[ORM\ManyToOne(targetEntity: Project::class, inversedBy: 'accidents')]
    #[ORM\JoinColumn(nullable: false)]
    private  $lieu_mecaniciens = null;




    public function getImage()
    {
        return $this->image;
    }


    public function setImage($image): self
    {
        $this->image = $image;

        return $this;
    }


    public function getId(): ?int
    {
        return $this->id;
    }

    public function addImage(Image $image): void
    {

        if (!$this->image->contrains($image)) {
            $this->image[] = $image;
            $image->setAccident($this);
        }
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

    public function getPrenomClient(): ?string
    {
        return $this->prenom_client;
    }

    public function setPrenomClient(string $prenom_client): self
    {
        $this->prenom_client = $prenom_client;

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


    public function getImageVoitureReparee()
    {
        return $this->image_voiture_reparee;
    }


    public function setImageVoitureReparee($image_voiture_reparee): void
    {
        $this->image_voiture_reparee = $image_voiture_reparee;
    }


    public function getImagePieceEndommage()
    {
        return $this->image_piece_endommage;
    }


    public function setImagePieceEndommage($image_piece_endommage): void
    {
        $this->image_piece_endommage = $image_piece_endommage;
    }


    public function getImageNouveauPieces()
    {
        return $this->image_nouveau_pieces;
    }

    public function setImageNouveauPieces($image_nouveau_pieces): void
    {
        $this->image_nouveau_pieces = $image_nouveau_pieces;
    }

    public function getImageFactureReparation()
    {
        return $this->image_facture_reparation;
    }


    public function setImageFactureReparation($image_facture_reparation): void
    {
        $this->image_facture_reparation = $image_facture_reparation;
    }

    public function getLieumecaniciens(): ?Project
    {
        return $this->lieu_mecaniciens;
    }

    public function setLieumecaniciens(?Project $lieu_mecaniciens): self
    {
        $this->lieu_mecaniciens = $lieu_mecaniciens;

        return $this;
    }






}
