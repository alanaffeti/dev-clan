<?php

namespace App\Form;

use App\Entity\Reponse;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;

class ReponseType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('nom_ag')
            ->add('prenom_ag')
          /* ->add('reponses',EntityType::class,
            ['class'=>Reponse::class,
            'choice_label'=>'id','multiple'=>false])*/

            ->add('traitement',ChoiceType::class, [
        'choices'  => [

            'Traitée' => 'Traitée',
            'Non Traitée' => 'non Traitée',
        ],]);

     /*   ->add('reclamation')
        ; */
            
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Reponse::class,
        ]);
    }
}

    