<?php

namespace App\Form;

use App\Entity\Accident;
use App\Entity\Project;
use Karser\Recaptcha3Bundle\Form\Recaptcha3Type;
use Karser\Recaptcha3Bundle\Validator\Constraints\Recaptcha3;
use phpDocumentor\Reflection\Types\False_;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\FileType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Validator\Constraints\File;

class AccidentType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder

            ->add('nom_client')
            ->add('prenom_client')
            ->add('matricule')
            ->add('lieu_mecaniciens',EntityType::class,
                ['class'=>Project::class,
                    'choice_label'=>'lieu_garage','multiple'=>false,'required'=>false,])


            ->add('image',FileType::class,[
              'label' => 'image voiture' ,
                'mapped' => true,
                'required' => false,
                'multiple' => true,


            ])
            ->add('image_voiture_reparee',FileType::class,[
                'mapped' => true,
                'required' => false,
                'multiple' => true,


            ])
            ->add('image_piece_endommage',FileType::class,[

                'mapped' => true,
                'required' => false,
                'multiple' => true,


            ])
            ->add('image_nouveau_pieces',FileType::class,[

                'mapped' => true,
                'required' => false,
                'multiple' => true,



            ])
            ->add('image_facture_reparation',FileType::class,[
                'mapped' => true,
                'required' => false,
                'multiple' => true,


            ])
            ->add('captcha', Recaptcha3Type::class, [
                'constraints' => new Recaptcha3(),
                'action_name' => 'accident',

            ]);
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Accident::class,
        ]);
    }

}