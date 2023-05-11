<?php

namespace App\Form;

use App\Entity\Facturemeca;
use Doctrine\DBAL\Types\TextType;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\TextareaType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class FacturemecaType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('article',TextareaType::class)
            ->add('quantite',TextareaType::class)
            ->add('prixsanstva',TextareaType::class)
            ->add('prixavectva',TextareaType::class)
            ->add('total',TextareaType::class)
            ->add('description', TextareaType::class)


        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Facturemeca::class,
        ]);
    }
}
